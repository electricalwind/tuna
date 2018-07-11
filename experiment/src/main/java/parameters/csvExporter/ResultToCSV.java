package parameters.csvExporter;

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

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import gitutils.FilesOfInterest;
import parameters.setup.Setup;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ResultToCSV {

    /**
     * Write a result of a project
     * @param result
     * @param fileName
     * @param directory
     * @throws IOException
     */
    public static void write(Map<Setup, Double> result, String fileName, String directory) throws IOException {

        List<String[]> toWrite = new LinkedList<>();
        String[] header = new String[]{"Smoother", "N-Gram Size", "Threshold", "Cross-entropy"};
        toWrite.add(header);

        for (Map.Entry<Setup, Double> entry : result.entrySet()) {
            Setup setup = entry.getKey();
            String[] line = new String[]{setup.smoother().getName(), String.valueOf(setup.ngramSize()), String.valueOf(setup.threshold()), String.valueOf(entry.getValue())};
            toWrite.add(line);
        }
        File v = new File(directory + fileName + ".csv");
        CSVWriter writerv = new CSVWriter(new FileWriter(v, false), ';');
        writerv.writeAll(toWrite);
        writerv.close();
    }

    /**
     * merge all result
     * @param pathFrom
     * @param pathTo
     * @throws IOException
     */
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

}
