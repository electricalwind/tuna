package parameters;

import parameters.result.VocabularyGrowthResult;

import java.util.*;

@SuppressWarnings("Duplicates")
public class VocabularyGrowth {


    public static VocabularyGrowthResult run(List<Iterable<String>> set) {
        Set<String> vocabulary = new HashSet<>();
        int total = 0;

        List<Integer> values = new LinkedList<>();

        for (Iterable<String> doc : set) {
            Iterator<String> it = doc.iterator();
            while (it.hasNext()) {
                total++;
                vocabulary.add(it.next());
                if (total % 1000 == 0) {
                    values.add(vocabulary.size());
                }
            }
        }
        values.add(vocabulary.size());

        return new VocabularyGrowthResult(total, values);
    }
}
