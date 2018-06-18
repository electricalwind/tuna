package parameters.analysis;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import gitutils.FilesOfInterest;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvAnalysis {

    public static void produceGenericCSV(String pathFrom, String pathTo) throws IOException {
        List<String> csvs = FilesOfInterest.list(pathFrom, "csv");

        Map<String, List<String>> mapProjectToResultCSV = new HashMap<>();
        for (int i = 0; i < csvs.size(); i++) {
            String csv = csvs.get(i);
            String[] ids = csv.split("_");
            StringBuilder builder = new StringBuilder();
            for (int j = 1; j < ids.length; j++) {
                builder.append(ids[j]);
            }
            String project = builder.toString();
            if (mapProjectToResultCSV.containsKey(project)) {
                mapProjectToResultCSV.get(project).add(csv);
            } else {
                List<String> csvFiles = new ArrayList<>();
                csvFiles.add(csv);
                mapProjectToResultCSV.put(project, csvFiles);
            }
        }

        for (Map.Entry<String, List<String>> entry : mapProjectToResultCSV.entrySet()) {

            CSVWriter writer = new CSVWriter(new FileWriter(new File(pathTo + entry.getKey()), false), ';');
            writer.writeNext(new String[]{"Tokenizer","Smoother","N-Gram Size","Threshold","Cross-Entropy"});
            for (String csv : entry.getValue()) {
                String[] ids = csv.split("/");
                String[] file = ids[ids.length - 1].split("_");
                String tokenizer = file[0];
                CSVReader reader = new CSVReader(new FileReader(new File(csv)), ';');
                List<String[]> data = reader.readAll();
                data.remove(0);
                List<String[]> newDate = new ArrayList<>();
                for (String[] d : data) {
                    String[] newD = new String[d.length+1];
                    newD[0]=tokenizer;
                    for(int i =0;i<d.length;i++){
                        newD[i+1] = d[i];
                    }
                    newDate.add(newD);
                }
                writer.writeAll(newDate);
            }
            writer.close();
        }

    }

    public static void main(String[] args) throws IOException {
        produceGenericCSV("/Users/matthieu/Documents/sanerrq1/","/Users/matthieu/Documents/sanerrq1/result/");
    }
}
