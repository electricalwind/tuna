package parameters.result;


import com.opencsv.CSVWriter;
import modelling.util.assertion.Assert;

import java.util.List;

@SuppressWarnings("Duplicates")
public class VocabularyGrowthResult {

    private int maxValue;
    private List<Integer> values;

    public VocabularyGrowthResult(int maxValue, List<Integer> values) {
        Assert.isTrue(maxValue / 1000 + 1 == values.size());

        this.maxValue = maxValue;
        this.values = values;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public List<Integer> getValues() {
        return values;
    }

    public CSVWriter addToCSV(CSVWriter writer, String project) {
        String[] newData = new String[maxValue / 1000 + 1];
        newData[0] = project;
        int j = 1;
        for (int i = 0; i < values.size() - 1; i++) {
            newData[j] = String.valueOf(values.get(i));
            j++;
        }
        writer.writeNext(newData);

        return writer;
    }
}
