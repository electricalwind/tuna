package defectstudy;

/*-
 * #%L
 * tuna
 * %%
 * Copyright (C) 2018 Maxime Cordy, Matthieu Jimenez, University of Luxembourg and Namur
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */


import dataset.BugDatasetGenerator;
import dataset.Utils;
import dataset.model.Bug;
import dataset.model.Fix;
import dataset.model.Software;
import dataset.setup.Softwares;
import defectstudy.callable.CountLine;
import defectstudy.callable.TokenizeF;
import defectstudy.model.BugResult;
import defectstudy.model.BugTokenFile;
import defectstudy.model.ReleaseFile;
import defectstudy.model.ReleaseResult;
import gitutils.FilesOfInterest;
import gitutils.gitUtilitaries.GitActions;
import modelling.NgramModel;
import modelling.exception.TrainingFailedException;
import modelling.infrastructure.NgramModelKylmImpl;
import modelling.infrastructure.kylm.ngram.smoother.MKNSmoother;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.main.kylm.SmootherFactory;
import modelling.util.Pair;
import org.eclipse.jgit.api.errors.GitAPIException;
import tokenizer.file.AbstractFileTokenizer;
import tokenizer.file.java.exception.UnparsableException;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static tokenizer.file.JavaFileTokenizerFactory.*;

@SuppressWarnings("Duplicates")
public class Application {

    private static final int NBTHREADS = 4;

    private static Map<String, AbstractFileTokenizer> createTokenizers() {
        Map<String, AbstractFileTokenizer> result = new LinkedHashMap<>();

        putTokenizer(result, utfTokenizer());
        putTokenizer(result, utfWocTokenizer());
        putTokenizer(result, lemmeTokenizer());
        putTokenizer(result, lemmeWocTokenizer());
        putTokenizer(result, depthFirstTokenizer());
        putTokenizer(result, breadthFirstTokenizer());
        putTokenizer(result, depthFirstPrunedTokenizer());
        putTokenizer(result, breadthFirstPrunedTokenizer());

        return result;
    }

    private static void putTokenizer(
            Map<String, AbstractFileTokenizer> map, AbstractFileTokenizer tokenizer) {
        map.put(tokenizer.getType(), tokenizer);
    }

    private final List<Software> SOFTWARES = Softwares.getAll();
    private final Map<String, AbstractFileTokenizer> TOKENIZERS = createTokenizers();
    private final String DIRECTORY;
    private final int n = 4;
    private final NgramSmoother SMOOTHER = SmootherFactory.create(MKNSmoother.ABV);

    public Application(String directory) {
        DIRECTORY = directory;
    }

    public static void main(String[] args) throws GitAPIException, IOException, TrainingFailedException, ClassNotFoundException, UnparsableException {
        if(args.length!=1){
            throw new RuntimeException("wrong number of Arguments");
        }
        Application app = new Application(args[0]);
        app.run();
    }

    public void run() throws GitAPIException, IOException, TrainingFailedException, ClassNotFoundException, UnparsableException {
        List<Pair<Software, List<Bug>>> listbugs = new ArrayList<>();

        for (Software software : SOFTWARES) {
            String pathGit = DIRECTORY + software.getAbreviation() + "/";
            File bugf = new File(pathGit + "bugs.obj");
            List<Bug> bugs;
            if (!bugf.exists()) {
                bugs = new BugDatasetGenerator(software, pathGit).retrieveBugs();
                BugDatasetGenerator.saveBugs(bugs, bugf.getAbsolutePath());
            } else {
                bugs = BugDatasetGenerator.loadBugs(bugf.getAbsolutePath());
            }
            listbugs.add(new Pair<>(software, bugs));
        }

        for (Pair<Software, List<Bug>> pair : listbugs) {

            Software software = pair.getFirst();
            List<Bug> bugs = pair.getSecond();

            String pathGit = DIRECTORY + software.getAbreviation() + "/";
            System.out.println("Starting soft: " + software.getAbreviation());

            GitActions git = new GitActions(pathGit);
            List<Pair<String, Integer>> versions = Utils.getOrderedVersionOfAsoftware(software, pathGit);

            Map<String, List<Bug>> bugPerVersion = Utils.reorderListBugs(bugs);
            Map<String, List<Bug>> fixPerVersion = fixedBugVersion(bugs, versions, git);

            List<Pair<Integer, Bug>> versionLessBug = Utils.orderedVersionLessBugs(bugs, pathGit);
            Map<String, List<Bug>> previousVersionBugLess = previousVersionforBugLess(versionLessBug, versions);
            Map<String, List<Bug>> nextVersionBugLess = nextVersionforBugLess(versionLessBug, versions);

            Map<BugTokenFile, List<BugResult>> bugsMap = new HashMap<>();
            for (Pair<String, Integer> version : versions) {
                System.out.println("Starting version: " + version.getFirst());
                git.resetHardToCommit(software.getVersions().get(version.getFirst()));

                List<String> paths = FilesOfInterest.list(pathGit, "java");
                paths = paths.stream().filter(path -> !(path.contains("test") || path.contains("example"))).collect(Collectors.toList());
                Map<String, Integer> locPerFile = multiThreadedCountLines(paths, pathGit);

                Map<String, List<Bug>> thisVersionsBugs = Utils.buggedFile(bugPerVersion.getOrDefault(version.getFirst(), new ArrayList<>()));
                Map<String, List<Bug>> thisVersionPatched = Utils.fixedFile(fixPerVersion.getOrDefault(version.getFirst(), new ArrayList<>()));

                Map<String, List<Bug>> buglessToConsider = Utils.buggedFile(previousVersionBugLess.getOrDefault(version.getFirst(), new ArrayList<>()));
                Map<String, List<Bug>> fixlessToConsider = Utils.fixedFile(nextVersionBugLess.getOrDefault(version.getFirst(), new ArrayList<>()));

                Map<ReleaseFile, List<ReleaseResult>> resultForRelease = new HashMap<>();
                for (Map.Entry<String, AbstractFileTokenizer> tokenizer : TOKENIZERS.entrySet()) {
                    System.out.println("Starting Tokenizer: " + tokenizer.getKey());
                    List<Map.Entry<String, Iterable<String>>> tokenizedContent = new ArrayList<>(multiThreadedTokenization(tokenizer.getValue(), paths, pathGit).entrySet());
                    for (int i = 0; i < tokenizedContent.size(); i++) {
                        Collections.swap(tokenizedContent, i, 0);
                        System.out.println("i: " + i + "/" + tokenizedContent.size());
                        List<Iterable<String>> trainingSet = tokenizedContent.subList(1, tokenizedContent.size()).stream().map(Map.Entry::getValue).collect(Collectors.toList());
                        evaluation(bugsMap, version, locPerFile, thisVersionsBugs, thisVersionPatched, buglessToConsider, fixlessToConsider, resultForRelease, tokenizer, tokenizedContent, trainingSet, 1);
                        evaluation(bugsMap, version, locPerFile, thisVersionsBugs, thisVersionPatched, buglessToConsider, fixlessToConsider, resultForRelease, tokenizer, tokenizedContent, trainingSet, 8);
                    }
                }
                saverelease(resultForRelease, pathGit, software.getAbreviation(), version.getFirst());
                System.out.println("Finished version: " + version.getFirst());
            }
            saveBugs(bugsMap, pathGit, software.getAbreviation());
        }
    }

    private void evaluation(Map<BugTokenFile, List<BugResult>> bugsMap, Pair<String, Integer> version, Map<String, Integer> locPerFile, Map<String, List<Bug>> thisVersionsBugs, Map<String, List<Bug>> thisVersionPatched, Map<String, List<Bug>> buglessToConsider, Map<String, List<Bug>> fixlessToConsider, Map<ReleaseFile, List<ReleaseResult>> resultForRelease, Map.Entry<String, AbstractFileTokenizer> tokenizer, List<Map.Entry<String, Iterable<String>>> tokenizedContent, List<Iterable<String>> trainingSet, int threshold) throws TrainingFailedException, IOException, UnparsableException {
        NgramModel model1 = new NgramModelKylmImpl(n, SMOOTHER, threshold);
        model1.train(trainingSet);
        String file = tokenizedContent.get(0).getKey();
        double fileEntropy = model1.crossEntropy(tokenizedContent.get(0).getValue());
        int countBug = 0;
        int countFixed = 0;
        if (!file.contains("test") && !file.contains("examples")) {
            if (thisVersionsBugs.containsKey(file)) {
                for (Bug bug : thisVersionsBugs.get(file)) {
                    for (Fix fix : bug.getFixes()) {
                        if (fix.getFileBefore().getFilePath().equals(file)) {
                            countBug++;
                            Iterable<String> before = tokenizer.getValue().tokenize(fix.getFileBefore().getFileContent());
                            Iterable<String> after = tokenizer.getValue().tokenize(fix.getFileAfter().getFileContent());
                            double bugEntropy = Double.POSITIVE_INFINITY;
                            double FixEntropy = Double.POSITIVE_INFINITY;
                            if (before != null)
                                bugEntropy = model1.crossEntropy(before);
                            if (after != null)
                                FixEntropy = model1.crossEntropy(after);
                            BugTokenFile bugTokenFile = new BugTokenFile(file, bug.getIdentifier(), tokenizer.getKey() + threshold);
                            BugResult bugResult = new BugResult();
                            bugResult.setReleaseAffected(version.getFirst());
                            bugResult.setBerelease(String.valueOf(fileEntropy));
                            bugResult.setBuggy(String.valueOf(bugEntropy));
                            bugResult.setFixed(String.valueOf(FixEntropy));
                            if (bugsMap.containsKey(bugTokenFile)) {
                                bugsMap.get(bugTokenFile).add(bugResult);
                            } else {
                                List<BugResult> result = new ArrayList<>();
                                result.add(bugResult);
                                bugsMap.put(bugTokenFile, result);
                            }
                        }
                    }
                }
            }

            if (thisVersionPatched.containsKey(file)) {
                for (Bug bug : thisVersionPatched.get(file)) {
                    for (Fix fix : bug.getFixes()) {
                        if (fix.getFileAfter().getFilePath().equals(file)) {
                            BugTokenFile bugTokenFile = new BugTokenFile(fix.getFileBefore().getFilePath(), bug.getIdentifier(), tokenizer.getKey() + threshold);
                            if (bugsMap.containsKey(bugTokenFile)) {
                                countFixed++;
                                bugsMap.get(bugTokenFile).forEach(bugResult -> bugResult.setAfrelease(String.valueOf(fileEntropy)));
                            }

                        }
                    }
                }

            }
            if (buglessToConsider.containsKey(file)) {
                for (Bug bug : buglessToConsider.get(file)) {
                    for (Fix fix : bug.getFixes()) {
                        if (fix.getFileBefore().getFilePath().equals(file)) {
                            double bugEntropy = model1.crossEntropy(tokenizer.getValue().tokenize(fix.getFileBefore().getFileContent()));
                            double FixEntropy = model1.crossEntropy(tokenizer.getValue().tokenize(fix.getFileAfter().getFileContent()));
                            BugTokenFile bugTokenFile = new BugTokenFile(file, bug.getIdentifier(), tokenizer.getKey() + threshold);
                            BugResult bugResult = new BugResult();
                            bugResult.setBerelease(String.valueOf(fileEntropy));
                            bugResult.setBuggy(String.valueOf(bugEntropy));
                            bugResult.setFixed(String.valueOf(FixEntropy));
                            if (bugsMap.containsKey(bugTokenFile)) {
                                bugsMap.get(bugTokenFile).add(bugResult);
                            } else {
                                List<BugResult> result = new ArrayList<>();
                                result.add(bugResult);
                                bugsMap.put(bugTokenFile, result);
                            }
                        }
                    }
                }

            }
            if (fixlessToConsider.containsKey(file)) {
                for (Bug bug : fixlessToConsider.get(file)) {
                    for (Fix fix : bug.getFixes()) {
                        if (fix.getFileAfter().getFilePath().equals(file)) {
                            BugTokenFile bugTokenFile = new BugTokenFile(fix.getFileBefore().getFilePath(), bug.getIdentifier(), tokenizer.getKey() + threshold);
                            if (bugsMap.containsKey(bugTokenFile)) {
                                bugsMap.get(bugTokenFile).forEach(bugResult -> bugResult.setAfrelease(String.valueOf(fileEntropy)));
                            }
                        }
                    }
                }
            }

        }
        ReleaseFile releaseFile = new ReleaseFile(file, countBug, countFixed, locPerFile.get(file));
        ReleaseResult releaseResult = new ReleaseResult(tokenizer.getKey() + threshold, String.valueOf(fileEntropy));
        if (resultForRelease.containsKey(releaseFile)) {
            resultForRelease.get(releaseFile).add(releaseResult);
        } else {
            List<ReleaseResult> releaseResults = new ArrayList<>();
            releaseResults.add(releaseResult);
            resultForRelease.put(releaseFile, releaseResults);
        }
    }


    private Map<String, List<Bug>> fixedBugVersion(List<Bug> bugs, List<Pair<String, Integer>> versions, GitActions gitUtilitary) throws IOException {
        Map<String, List<Bug>> fixmap = new HashMap<>();
        for (Bug bug : bugs) {
            if (bug.getAffectedVersion().size() > 0) {
                int time = gitUtilitary.getTimeCommit(bug.getHash());
                for (Pair<String, Integer> version : versions) {
                    if (time < version.getSecond()) {
                        if (fixmap.containsKey(version.getFirst())) {
                            fixmap.get(version.getFirst()).add(bug);
                        } else {
                            List<Bug> bugList = new ArrayList<>();
                            bugList.add(bug);
                            fixmap.put(version.getFirst(), bugList);
                        }
                        break;
                    }
                }
            }
        }
        return fixmap;
    }

    private Map<String, List<Bug>> previousVersionforBugLess(List<Pair<Integer, Bug>> bugs, List<Pair<String, Integer>> versions) {
        Map<String, List<Bug>> previous = new HashMap<>();
        for (Pair<Integer, Bug> bug : bugs) {
            int time = bug.getFirst();

            for (int i = versions.size() - 1; i >= 0; i--) {

                if (time >= versions.get(i).getSecond()) {
                    if (previous.containsKey(versions.get(i).getFirst())) {
                        previous.get(versions.get(i).getFirst()).add(bug.getSecond());
                    } else {
                        List<Bug> bugList = new ArrayList<>();
                        bugList.add(bug.getSecond());
                        previous.put(versions.get(i).getFirst(), bugList);
                    }
                    break;
                }
            }
        }
        return previous;
    }

    private Map<String, List<Bug>> nextVersionforBugLess(List<Pair<Integer, Bug>> bugs, List<Pair<String, Integer>> versions) {
        Map<String, List<Bug>> previous = new HashMap<>();
        for (Pair<Integer, Bug> bug : bugs) {
            int time = bug.getFirst();
            for (Pair<String, Integer> version : versions) {
                if (time <= version.getSecond()) {
                    if (previous.containsKey(version.getFirst())) {
                        previous.get(version.getFirst()).add(bug.getSecond());
                    } else {
                        List<Bug> bugList = new ArrayList<>();
                        bugList.add(bug.getSecond());
                        previous.put(version.getFirst(), bugList);
                    }
                    break;
                }
            }
        }
        return previous;
    }


    private Map<String, Iterable<String>> multiThreadedTokenization(AbstractFileTokenizer tokenizer, List<String> toTokenize, String pathToRemove) {
        Map<String, Iterable<String>> results = new HashMap<>();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NBTHREADS);
        CompletionService<Pair<String, Iterable<String>>> completionService = new ExecutorCompletionService<>(executor);

        int count = 0;

        for (String id : toTokenize) {
            TokenizeF t = new TokenizeF(id, tokenizer, pathToRemove);
            completionService.submit(t);
            count++;
        }

        int received = 0;
        while (received < count) {
            try {
                Future<Pair<String, Iterable<String>>> futurResult = completionService.take();
                Pair<String, Iterable<String>> result = futurResult.get();
                received++;
                //System.out.println("Mutant " + received + "/" + count);
                if (result != null && result.getSecond() != null) {
                    results.put(result.getFirst(), result.getSecond());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return results;
    }

    private Map<String, Integer> multiThreadedCountLines(List<String> toCount, String pathToRemove) {
        Map<String, Integer> results = new HashMap<>();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NBTHREADS);
        CompletionService<Pair<String, Integer>> completionService = new ExecutorCompletionService<>(executor);

        int count = 0;

        for (String id : toCount) {
            CountLine t = new CountLine(id, pathToRemove);
            completionService.submit(t);
            count++;
        }

        int received = 0;
        while (received < count) {
            try {
                Future<Pair<String, Integer>> futurResult = completionService.take();
                Pair<String, Integer> result = futurResult.get();
                received++;
                //System.out.println("Mutant " + received + "/" + count);
                if (result != null) {
                    results.put(result.getFirst(), result.getSecond());
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
        return results;
    }


    public static void saverelease(Map<ReleaseFile, List<ReleaseResult>> releaseresult, String folder, String project, String release) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(folder + project + "_" + release + ".obj", false);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(releaseresult);

        out.close();
        fileOut.close();
    }

    public static Map<ReleaseFile, List<ReleaseResult>> loadrelease(String folder, String project, String release) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(folder + project + "_" + release + ".obj");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map<ReleaseFile, List<ReleaseResult>> releaseR = (Map<ReleaseFile, List<ReleaseResult>>) in.readObject();
        return releaseR;
    }


    public static void saveBugs(Map<BugTokenFile, List<BugResult>> bugs, String folder, String project) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(folder + project + "bugs.obj", false);
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(bugs);
        out.close();
        fileOut.close();
    }

    public static Map<BugTokenFile, List<BugResult>> loadBugs(String folder, String project) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(folder + project + "bugs.obj");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map<BugTokenFile, List<BugResult>> bugs = (Map<BugTokenFile, List<BugResult>>) in.readObject();
        return bugs;
    }

}
