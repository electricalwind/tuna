package defectstudy.csvExporter;


import com.opencsv.CSVWriter;
import defectstudy.model.BugResult;
import defectstudy.model.BugTokenFile;
import gitutils.FilesOfInterest;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyseBugs {

    private static void bugtoCSV(String s) throws IOException, ClassNotFoundException {
        List<String> resultBugs = FilesOfInterest.list(s, "obj");
        for (String resul : resultBugs) {
            Map<BugTokenFile, List<BugResult>> release = loadBugs(resul);
            String prorel = resul.replace(".obj", ".csv");
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(prorel), false));
            writerom.writeNext(new String[]{"File", "budID", "Tokenizer", "berelease", "buggy", "fixed", "afrelease", "releaseAffected"});
            for (Map.Entry<BugTokenFile, List<BugResult>> file : release.entrySet()) {
                for (BugResult bugResult : file.getValue()) {
                    String[] fileres = new String[8];
                    fileres[0] = file.getKey().getFile();
                    fileres[1] = String.valueOf(file.getKey().getBudID());
                    String tok = switchingtok(file.getKey().getTokenizerAbv());
                    if (tok != null) {
                        fileres[2] = tok;
                        fileres[3] = bugResult.getBerelease();
                        fileres[4] = bugResult.getBuggy();
                        fileres[5] = bugResult.getFixed();
                        if (bugResult.getAfrelease() != null)
                            fileres[6] = bugResult.getAfrelease();
                        else {
                            fileres[6] = "NA";
                        }
                        if (bugResult.getReleaseAffected() != null)
                            fileres[7] = bugResult.getReleaseAffected();
                        else {
                            fileres[7] = "0.0";
                        }
                        writerom.writeNext(fileres);
                    }
                }
            }
            writerom.close();
        }
    }

    public static String switchingtok(String abv) {
        switch (abv) {
            case "UTFTokenizer1":
                return "UTF";
            case "UTFTokenizer8":
                return null;
            case "UTFWOCJTokenizer1":
                return "UTFw";
            case "UTFWOCJTokenizer8":
                return null;
            case "JavaLemmeTokenizer1":
                return "JP";
            case "JavaLemmeTokenizer8":
                return null;
            case "JavaLemmeWOCTokenizer1":
                return "JPw";
            case "JavaLemmeWOCTokenizer8":
                return null;
            case "Java,AST,Depth-first search1":
                return "DF";
            case "Java,AST,Depth-first search8":
                return null;
            case "Java,AST,Breadth-first search1":
                return "BF";
            case "Java,AST,Breadth-first search8":
                return null;
            case "Java,AST (pruned),Depth-first search1":
                return "PDF";
            case "Java,AST (pruned),Depth-first search8":
                return null;
            case "Java,AST (pruned),Breadth-first search1":
                return "PBF";
            case "Java,AST (pruned),Breadth-first search8":
                return null;
            default:
                return null;
        }
    }


    public static Map<BugTokenFile, List<BugResult>> loadBugs(String folder) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(folder);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map<BugTokenFile, List<BugResult>> bugs = (Map<BugTokenFile, List<BugResult>>) in.readObject();
        return bugs;
    }


    private static void bugtoCSV2(String s) throws IOException, ClassNotFoundException {
        List<String> resultBugs = FilesOfInterest.list(s, "obj");
        for (String resul : resultBugs) {
            Map<BugTokenFile, List<BugResult>> release = loadBugs(resul);
            String prorel = resul.replace(".obj", "tt.csv");
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(prorel), false));
            writerom.writeNext(new String[]{"UTF", "UTFw", "JP", "JPw", "DF", "BF", "PDF", "PBF"});
            Map<String,String[]> result  = new HashMap<>();
            for (Map.Entry<BugTokenFile, List<BugResult>> file : release.entrySet()) {
                String study = file.getKey().getBudID()+file.getKey().getFile();
                String[] tokenizerRes;
                if(result.containsKey(study)){
                    tokenizerRes =result.get(study);
                }else{
                    tokenizerRes = new String[]{"NA","NA","NA","NA","NA","NA","NA","NA"};
                    result.put(study,tokenizerRes);
                }
                for (BugResult bugResult : file.getValue()) {
                    int tok = switchingtok2(file.getKey().getTokenizerAbv());
                    if (tok != -1) {
                        double bug = Double.parseDouble(bugResult.getBuggy());
                        double fix = Double.parseDouble(bugResult.getFixed());
                        tokenizerRes[tok] = String.valueOf((bug -fix)*100/bug);
                    }
                }
            }
            writerom.writeAll(result.values());
            writerom.close();
        }
    }




    private static void bugtoCSV3(String s) throws IOException, ClassNotFoundException {
        List<String> resultBugs = FilesOfInterest.list(s, "obj");
        for (String resul : resultBugs) {
            Map<BugTokenFile, List<BugResult>> release = loadBugs(resul);
            String prorel = resul.replace(".obj", "ttttt.csv");
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(prorel), false));
            writerom.writeNext(new String[]{"UTF", "UTFw", "JP", "JPw", "DF", "BF", "PDF", "PBF"});
            Map<String,String[]> result  = new HashMap<>();
            for (Map.Entry<BugTokenFile, List<BugResult>> file : release.entrySet()) {
                String study = file.getKey().getBudID()+file.getKey().getFile();
                String[] tokenizerRes;
                if(result.containsKey(study)){
                    tokenizerRes =result.get(study);
                }else{
                    tokenizerRes = new String[]{"NA","NA","NA","NA","NA","NA","NA","NA"};
                    result.put(study,tokenizerRes);
                }
                for (BugResult bugResult : file.getValue()) {
                    int tok = switchingtok2(file.getKey().getTokenizerAbv());
                    if (tok != -1) {
                        double bug = Double.parseDouble(bugResult.getBuggy());
                        double fix = Double.parseDouble(bugResult.getFixed());
                        double res = bug-fix;
                        if(res ==0){tokenizerRes[tok] = "0";}
                        else if (res>0){tokenizerRes[tok] = "1";}
                        else{tokenizerRes[tok] = "-1";}
                    }
                }
            }
            writerom.writeAll(result.values());
            writerom.close();
        }
    }


    public static int switchingtok2(String abv) {
        switch (abv) {
            case "UTFTokenizer1":
                return 0;
            case "UTFTokenizer8":
                return -1;
            case "UTFWOCJTokenizer1":
                return 1;
            case "UTFWOCJTokenizer8":
                return -1;
            case "JavaLemmeTokenizer1":
                return 2;
            case "JavaLemmeTokenizer8":
                return -1;
            case "JavaLemmeWOCTokenizer1":
                return 3;
            case "JavaLemmeWOCTokenizer8":
                return -1;
            case "Java,AST,Depth-first search1":
                return 4;
            case "Java,AST,Depth-first search8":
                return -1;
            case "Java,AST,Breadth-first search1":
                return 5;
            case "Java,AST,Breadth-first search8":
                return -1;
            case "Java,AST (pruned),Depth-first search1":
                return 6;
            case "Java,AST (pruned),Depth-first search8":
                return -1;
            case "Java,AST (pruned),Breadth-first search1":
                return 7;
            case "Java,AST (pruned),Breadth-first search8":
                return -1;
            default:
                return -1;
        }
    }
}
