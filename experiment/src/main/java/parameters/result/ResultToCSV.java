package parameters.result;

import com.opencsv.CSVWriter;
import parameters.Setup;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ResultToCSV {

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


}
