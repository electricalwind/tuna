package dataset;


import dataset.model.Bug;
import dataset.model.Fix;
import dataset.model.Software;
import gitutils.gitUtilitaries.GitActions;
import modelling.util.Pair;

import java.io.IOException;
import java.util.*;

public class Utils {

    /**
     * reorder the software version in chronological order ant retrieve their release time from git
     * @param software
     * @param pathGit
     * @return
     */
    public static List<Pair<String, Integer>> getOrderedVersionOfAsoftware(Software software, String pathGit) throws IOException {
        List<Pair<String, Integer>> listVersion = new ArrayList<>(software.getVersions().size());
        GitActions git = new GitActions(pathGit);
        for (Map.Entry<String, String> version : software.getVersions().entrySet()) {
            int time = git.getTimeCommit(version.getValue());
            int i = 0;
            boolean stop = false;
            while (i < listVersion.size() && !stop) {
                Pair<String, Integer> versioni = listVersion.get(i);
                if (versioni.getSecond() < time) {
                    i++;
                } else if (versioni.getSecond() == time) {
                    String[] vi = listVersion.get(i).getFirst().split("\\.");
                    String[] vcurrent = version.getKey().split("\\.");
                    for (int j = 0; j < Math.max(vi.length, vcurrent.length); j++) {
                        if (j == vi.length) {
                            break;
                        }
                        if (j == vcurrent.length) {
                            stop = true;
                            break;
                        }
                        int ri = Integer.parseInt(vi[j]);
                        int rcurrent = Integer.parseInt(vcurrent[j]);
                        if (rcurrent < ri) {
                            stop = true;
                            break;
                        } else if (rcurrent > ri) {
                            break;
                        }
                    }
                    if (!stop) i++;
                } else {
                    stop = true;
                }
            }
            listVersion.add(i, new Pair<>(version.getKey(), time));
        }
        return listVersion;
    }

    /**
     * Order bug according to the version they affected
     * @param bugs
     * @return
     */
    public static Map<String, List<Bug>> reorderListBugs(List<Bug> bugs) {
        Map<String, List<Bug>> vbugs = new HashMap<>();
        for (Bug bug : bugs) {
            for (String version : bug.getAffectedVersion()) {
                if (!vbugs.containsKey(version)) {
                    List<Bug> bugList = new ArrayList<>();
                    bugList.add(bug);
                    vbugs.put(version, bugList);
                } else {
                    vbugs.get(version).add(bug);
                }
            }
        }
        return vbugs;
    }

    /**
     * Order bug without affected version according to the time they were patched
     * @param bugs
     * @param pathGit
     * @return
     */
    public static List<Pair<Integer, Bug>> orderedVersionLessBugs(List<Bug> bugs, String pathGit) throws IOException {
        List<Pair<Integer, Bug>> vlbugs = new LinkedList<>();
        GitActions git = new GitActions(pathGit);
        for (Bug bug : bugs) {
            if (bug.getAffectedVersion().size() == 0) {
                int time = git.getTimeCommit(bug.getHash());
                int i = 0;
                boolean stop = false;
                while (i < vlbugs.size() && !stop) {
                    if (vlbugs.get(i).getFirst() < time) {
                        i++;
                    } else {
                        stop = true;
                    }
                }
                vlbugs.add(i, new Pair<>(time, bug));
            }
        }
        return vlbugs;
    }

    /**
     * get for every bugged file the list of its corresponding bugs
     * @param bugs
     * @return
     */
    public static Map<String, List<Bug>> buggedFile(List<Bug> bugs) {
        Map<String, List<Bug>> bugged = new HashMap<>();
        for (Bug bug : bugs) {
            for (Fix fix : bug.getFixes()) {
                String path = fix.getFileBefore().getFilePath();
                if (bugged.containsKey(path)) {
                    bugged.get(path).add(bug);
                } else {
                    List<Bug> bugList = new ArrayList<>();
                    bugList.add(bug);
                    bugged.put(path, bugList);
                }
            }
        }
        return bugged;
    }

    /**
     * get for every fixed file the list og its corresponding bugs
     * @param bugs
     * @return
     */
    public static Map<String, List<Bug>> fixedFile(List<Bug> bugs) {
        Map<String, List<Bug>> fixed = new HashMap<>();
        for (Bug bug : bugs) {
            for (Fix fix : bug.getFixes()) {
                String path = fix.getFileAfter().getFilePath();
                if (fixed.containsKey(path)) {
                    fixed.get(path).add(bug);
                } else {
                    List<Bug> bugList = new ArrayList<>();
                    bugList.add(bug);
                    fixed.put(path, bugList);
                }
            }
        }
        return fixed;
    }

    /**
     * main for testing purposes
     * @param args
     */
    /**public static void main(String[] args) throws IOException {
        for (Software software : Softwares.getAll()) {
            List<Pair<String, Integer>> versions = getOrderedVersionOfAsoftware(software, "/Users/matthieu/Documents/programmation/exp/" + software.getAbreviation() + "/");

            System.out.println(software.getName());
            for (Pair<String, Integer> version : versions) {
                System.out.println(version.getFirst() + " : " + version.getSecond());
            }
        }
    }*/

}
