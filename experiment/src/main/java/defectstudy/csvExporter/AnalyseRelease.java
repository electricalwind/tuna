package defectstudy.csvExporter;


import com.opencsv.CSVWriter;
import defectstudy.model.ReleaseFile;
import defectstudy.model.ReleaseResult;
import gitutils.FilesOfInterest;

import java.io.*;
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

}
