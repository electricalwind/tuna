package parameters.setup;

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

import modelling.exception.TrainingFailedException;
import modelling.util.Pair;
import parameters.util.FoldPartition;
import parameters.util.KfoldIterator;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ExperimentSet {
    private final List<Iterable<String>> trainingSet;
    private final List<Iterable<String>> testingSet;
    private final SetupSet setups;
    private static final int FOLD_NUMBER = 2;
    private static final int NB_THREADS = 4;

    public ExperimentSet(List<Iterable<String>> documents) {
        trainingSet = documents;
        testingSet = null;
        this.setups = SetupSet.instance();
    }

    public ExperimentSet(List<Iterable<String>> training, List<Iterable<String>> testing) {
        trainingSet = training;
        testingSet = testing;
        this.setups = SetupSet.instance();
    }

    public Map<Setup, Double> run() {
        try {
            if (testingSet == null) {
                return runKfold();
            } else {
                return run(trainingSet, testingSet);
            }
        } catch (TrainingFailedException exception) {
            exception.printStackTrace();
            return null;
        }
    }

    private Map<Setup, Double> runKfold() throws TrainingFailedException {
        Iterator<FoldPartition<Iterable<String>>> kfold
                = new KfoldIterator(FOLD_NUMBER, trainingSet);

        Map<Setup, Double> results = new LinkedHashMap<>();
        int i = 1;
        while (kfold.hasNext()) {
            System.out.println("Starting fold " + i);
            i++;
            FoldPartition partition = kfold.next();
            Map<Setup, Double> intermediateResult = run(partition.trainingSet(), partition.testSet());
            for (Map.Entry<Setup, Double> entry : intermediateResult.entrySet()) {
                double formerResult = 0;
                if (results.containsKey(entry.getKey())) {
                    formerResult += results.get(entry.getKey());
                }
                double newResult = entry.getValue() + formerResult;
                results.put(entry.getKey(), newResult);
            }
        }
        for (Map.Entry<Setup, Double> entry : results.entrySet()) {
            results.put(entry.getKey(), entry.getValue() / FOLD_NUMBER);
        }
        return results;
    }

    private Map<Setup, Double> run(Iterable<Iterable<String>> trainingSet, Iterable<Iterable<String>> testingSet) {
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(NB_THREADS);
        CompletionService<Pair<Setup, Double>> completionService = new ExecutorCompletionService<>(executor);
        Map<Setup, Double> results = new LinkedHashMap<>();
        int count = 0;
        for (Setup setup : setups.setups()) {
            Experiment experiment = new Experiment(setup, trainingSet, testingSet);
            completionService.submit(experiment);
            count++;
        }
        int received = 0;
        while (received < count) {
            try {
                Future<Pair<Setup, Double>> futurResult = completionService.take();
                Pair<Setup, Double> result = futurResult.get();
                received++;
                System.out.println("setup " + received + "/" + count);
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

}
