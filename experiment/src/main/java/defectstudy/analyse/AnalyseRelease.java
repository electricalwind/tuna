package defectstudy.analyse;


import com.opencsv.CSVWriter;
import defectstudy.model.ReleaseFile;
import defectstudy.model.ReleaseResult;
import gitutils.FilesOfInterest;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AnalyseRelease {

    public static void releaseToCsv(String directory) throws IOException, ClassNotFoundException {
        List<String> resultRelease = FilesOfInterest.list(directory, "obj");
        for (String resul : resultRelease) {
            Map<ReleaseFile, List<ReleaseResult>> release = loadrelease(resul);
            String prorel = resul.replace(".obj", ".csv");
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(prorel), false));
            writerom.writeNext(new String[]{"File", "coutnBug", "countFix", "loc", "U1", "U8", "UW1", "UW8", "JL1", "JL8", "JLW1", "JLW8", "DF1", "DF8", "BF1", "BF8", "PDF1", "PDF8", "PBF1", "PBF8"});
            for (Map.Entry<ReleaseFile, List<ReleaseResult>> file : release.entrySet()) {
                //if (!file.getKey().getFile().contains("test")) {
                //if (!file.getKey().getFile().contains("example")) {
                String[] fileres = new String[20];
                fileres[0] = file.getKey().getFile();
                fileres[1] = String.valueOf(file.getKey().getBugs());
                fileres[2] = String.valueOf(file.getKey().getFixed());
                fileres[3] = String.valueOf(file.getKey().getLoc());
                for (int i = 0; i < 16; i++) {
                    if (i < file.getValue().size())
                        fileres[i + 4] = file.getValue().get(i).getEntropy();
                    else
                        fileres[i + 4] = "Infinity";
                }
                writerom.writeNext(fileres);
                //}
                //}
                /**if(file.getKey().getBugs()>0 && file.getKey().getFixed()>0){
                 fileres[1]= String.valueOf(file.getKey().getBugs());
                 fileres[2] ="0";
                 writerom.writeNext(fileres);
                 fileres[1]="0";
                 fileres[2] = String.valueOf(file.getKey().getFixed());
                 writerom.writeNext(fileres);
                 }else{
                 fileres[1] = String.valueOf(file.getKey().getBugs());
                 fileres[2] = String.valueOf(file.getKey().getFixed());
                 writerom.writeNext(fileres);
                 }*/

            }
            writerom.close();
        }

    }


    public static void release2ToCsv(String directory) throws IOException, ClassNotFoundException {
        List<String> resultRelease = FilesOfInterest.list(directory, "obj");
        for (String resul : resultRelease) {
            Map<ReleaseFile, List<ReleaseResult>> release = loadrelease(resul);
            String prorel = resul.replace(".obj", ".csv");
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(prorel), false));
            writerom.writeNext(new String[]{"File", "coutnBug", "countFix", "UTF", "UTFw", "JP", "JPw", "DF", "BF", "PDF", "PBF"});
            for (Map.Entry<ReleaseFile, List<ReleaseResult>> file : release.entrySet()) {
                //if (!file.getKey().getFile().contains("test")) {
                //if (!file.getKey().getFile().contains("example")) {
                String[] fileres = new String[11];
                fileres[0] = file.getKey().getFile();
                fileres[1] = String.valueOf(file.getKey().getBugs());
                fileres[2] = String.valueOf(file.getKey().getFixed());
                for (int i = 0; i < 8; i++) {
                    if (i * 2 < file.getValue().size())
                        fileres[i + 3] = file.getValue().get(i * 2).getEntropy();
                    else
                        fileres[i + 3] = "Infinity";
                }
                writerom.writeNext(fileres);
                //}
                //}
                /**if(file.getKey().getBugs()>0 && file.getKey().getFixed()>0){
                 fileres[1]= String.valueOf(file.getKey().getBugs());
                 fileres[2] ="0";
                 writerom.writeNext(fileres);
                 fileres[1]="0";
                 fileres[2] = String.valueOf(file.getKey().getFixed());
                 writerom.writeNext(fileres);
                 }else{
                 fileres[1] = String.valueOf(file.getKey().getBugs());
                 fileres[2] = String.valueOf(file.getKey().getFixed());
                 writerom.writeNext(fileres);
                 }*/

            }
            writerom.close();
        }

    }


    public static Map<ReleaseFile, List<ReleaseResult>> loadrelease(String folder) throws IOException, ClassNotFoundException {
        FileInputStream fileIn = new FileInputStream(folder);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Map<ReleaseFile, List<ReleaseResult>> releaseR = (Map<ReleaseFile, List<ReleaseResult>>) in.readObject();
        return releaseR;
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        releaseAnalysis3("/Users/matthieu/Documents/sanerRQ2/");
    }


    public static void releaseAnalysis(String directory) throws IOException, ClassNotFoundException {
        List<String> resultRelease = FilesOfInterest.list(directory, "obj");
        Map<String, Map<String, double[]>> result = new HashMap<>();
        for (String resul : resultRelease) {
            String[] p = resul.split("_");
            Map<ReleaseFile, List<ReleaseResult>> release = loadrelease(resul);
            double[] releaseSum = new double[16];
            int[] releaseInc = new int[16];
            for (Map.Entry<ReleaseFile, List<ReleaseResult>> file : release.entrySet()) {
                for (int i = 0; i < file.getValue().size(); i++) {
                    releaseSum[i] += Double.parseDouble(file.getValue().get(i).getEntropy());
                    releaseInc[i]++;
                }
            }
            for (int i = 0; i < 16; i++) {
                releaseSum[i] /= releaseInc[i];
            }
            if (result.containsKey(p[0])) {
                result.get(p[0]).put(p[1], releaseSum);
            } else {
                Map<String, double[]> projResult = new HashMap<>();
                projResult.put(p[1], releaseSum);
                result.put(p[0], projResult);
            }
        }

        for (Map.Entry<String, Map<String, double[]>> resul : result.entrySet()) {
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(resul.getKey() + "relres.csv"), false));
            writerom.writeNext(new String[]{"Version", "U1", "U8", "UW1", "UW8", "JL1", "JL8", "JLW1", "JLW8", "DF1", "DF8", "BF1", "BF8", "PDF1", "PDF8", "PBF1", "PBF8"});
            for (Map.Entry<String, double[]> file : resul.getValue().entrySet()) {
                String[] relReas = new String[17];
                relReas[0] = file.getKey();
                for (int i = 0; i < 16; i++) {
                    relReas[i + 1] = String.valueOf(file.getValue()[i]);
                }
                writerom.writeNext(relReas);
            }
            writerom.close();
        }

    }


    public static void releaseAnalysis2(String directory) throws IOException, ClassNotFoundException {
        List<String> resultRelease = FilesOfInterest.list(directory, "obj");
        Map<String, List<String[]>> result = new HashMap<>();
        String[] tokenizers = new String[]{"UTF", "UTFw", "JP", "JPw", "DF", "BF", "PDF", "PBF"};
        for (String resul : resultRelease) {
            String[] p = resul.split("_");
            List<String[]> table;
            if (result.containsKey(p[0])) {
                table = result.get(p[0]);
            } else {
                table = new ArrayList<>();
                result.put(p[0], table);
            }
            Map<ReleaseFile, List<ReleaseResult>> release = loadrelease(resul);
            double countBug = 0;
            double countFile = 0;
            List<String[]> intermediate = new ArrayList<>();
            for (Map.Entry<ReleaseFile, List<ReleaseResult>> file : release.entrySet()) {
                String buggy = file.getKey().getBugs() > 0 ? "Buggy" : "Not Buggy";
                if (file.getKey().getBugs() > 0) countBug++;
                countFile++;
                for (int i = 0; i * 2 < file.getValue().size(); i++) {
                    String entropy = file.getValue().get(i * 2).getEntropy();
                    String tokenizer = tokenizers[i];
                    intermediate.add(new String[]{buggy, entropy, tokenizer});
                }
            }
            //if (countBug * 100 / countFile > 5) {
            table.addAll(intermediate);
            // }

        }
        for (Map.Entry<String, List<String[]>> resul : result.entrySet()) {
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(resul.getKey() + "relres.csv"), false));
            writerom.writeNext(new String[]{"Buggy", "Entropy", "Tokenizer"});
            writerom.writeAll(resul.getValue());
            writerom.close();
        }

    }

    public static void releaseAnalysis3(String directory) throws IOException, ClassNotFoundException {
        List<String> resultRelease = FilesOfInterest.list(directory, "obj");
        Map<String, List<String[]>> result = new HashMap<>();
        String[] tokenizers = new String[]{"UTF", "UTF8", "DF", "DF8"};
        for (String resul : resultRelease) {
            String[] p = resul.split("_");
            List<String[]> table;
            if (result.containsKey(p[0])) {
                table = result.get(p[0]);
            } else {
                table = new ArrayList<>();
                result.put(p[0], table);
            }
            Map<ReleaseFile, List<ReleaseResult>> release = loadrelease(resul);
            List<String[]> intermediate = new ArrayList<>();
            for (Map.Entry<ReleaseFile, List<ReleaseResult>> file : release.entrySet()) {
                String buggy = file.getKey().getBugs() > 0 ? "Buggy" : "Not Buggy";
                String entropy = file.getValue().get(0).getEntropy();
                String tokenizer = tokenizers[0];
                intermediate.add(new String[]{buggy, entropy, tokenizer});
                entropy = file.getValue().get(1).getEntropy();
                tokenizer = tokenizers[1];
                intermediate.add(new String[]{buggy, entropy, tokenizer});
                if (file.getValue().size() > 8) {
                    entropy = file.getValue().get(8).getEntropy();
                    tokenizer = tokenizers[2];
                    intermediate.add(new String[]{buggy, entropy, tokenizer});
                    entropy = file.getValue().get(9).getEntropy();
                    tokenizer = tokenizers[3];
                    intermediate.add(new String[]{buggy, entropy, tokenizer});
                }
            }
            table.addAll(intermediate);


        }
        for (Map.Entry<String, List<String[]>> resul : result.entrySet()) {
            CSVWriter writerom = new CSVWriter(new FileWriter(new File(resul.getKey() + "relres.csv"), false));
            writerom.writeNext(new String[]{"Buggy", "Entropy", "Tokenizer"});
            writerom.writeAll(resul.getValue());
            writerom.close();
        }

    }

}
