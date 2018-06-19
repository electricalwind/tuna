package defectstudy.csvExporter;


import com.opencsv.CSVWriter;
import defectstudy.model.BugResult;
import defectstudy.model.BugTokenFile;
import gitutils.FilesOfInterest;

import java.io.*;
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
                return "UTF8";
            case "UTFWOCJTokenizer1":
                return "UTFw";
            case "UTFWOCJTokenizer8":
                return "UTFw8";
            case "JavaLemmeTokenizer1":
                return "JP";
            case "JavaLemmeTokenizer8":
                return "JP";
            case "JavaLemmeWOCTokenizer1":
                return "JPw";
            case "JavaLemmeWOCTokenizer8":
                return "JPw8";
            case "Java,AST,Depth-first search1":
                return "DF";
            case "Java,AST,Depth-first search8":
                return "DF8";
            case "Java,AST,Breadth-first search1":
                return "BF";
            case "Java,AST,Breadth-first search8":
                return "BF8";
            case "Java,AST (pruned),Depth-first search1":
                return "PDF";
            case "Java,AST (pruned),Depth-first search8":
                return "PDF8";
            case "Java,AST (pruned),Breadth-first search1":
                return "PBF";
            case "Java,AST (pruned),Breadth-first search8":
                return "PBF8";
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

}
