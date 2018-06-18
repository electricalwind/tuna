package modelling.infrastructure;

import java.util.LinkedList;
import java.util.List;

import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.util.MathUtils;
import modelling.util.assertion.Assert;
import modelling.NgramModel;
import modelling.exception.TrainingFailedException;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;

/**
 * Implementation of NaturalnessModel based on the Kyoto Language Model
 */
public class NgramModelKylmImpl implements NgramModel {

    private final int size;
    private final NgramLM model;
    private boolean trained;

    /**
     * @requires size > 1 && smoother not null
     * @effects Make this be a new NgramModel with this.size = size and
     * trained = false.
     */
    public NgramModelKylmImpl(int size, NgramSmoother smoother) {
        Assert.isTrue(size > 1);
        Assert.notNull(smoother);

        this.size = size;
        trained = false;

        this.model = new NgramLM(size, smoother);

        /**if (added) {
         //smoother.setCutoffs(new int[3]);
         //smoother.setSmoothUnigrams(true);
         model.setVocabFrequency(10);

         }*/
    }

    /**
     * @requires size > 1 && smoother not null
     * @effects Make this be a new NgramModel with this.size = size and
     * trained = false.
     */
    public NgramModelKylmImpl(int size, NgramSmoother smoother, int threshold) {
        Assert.isTrue(size > 1);
        Assert.isTrue(threshold > 0);
        Assert.notNull(smoother);

        this.size = size;
        trained = false;

        this.model = new NgramLM(size, smoother);
        this.model.setVocabFrequency(threshold);
    }

    @Override
    public void train(Iterable<Iterable<String>> documents)
            throws TrainingFailedException {
        try {
            this.model.trainModel(iterablesToArrays(documents));
            trained = true;
        } catch (Exception ex) {
            throw new TrainingFailedException(ex);
        }
    }

    @Override
    public double crossEntropy(Iterable<String> document) {
        Assert.isTrue(trained);
        String[] array;
        if (document != null) {
            array = iterableToArray(document);
        } else {
            array = new String[0];
        }
        //return -invert(array.length) * this.model.getSentenceProb(array);
        float words = 0;
        float[] wordEnts;
        int wordCount = 0;
        wordCount += array.length;
        wordEnts = model.getWordEntropies(array);
        words += MathUtils.sum(wordEnts);
        final float log2 = (float) Math.log10(2);
        words /= wordCount * log2 * -1;
        return words;
    }

    @Override
    public double averageCrossEntropy(Iterable<Iterable<String>> sentences) {
        Assert.isTrue(trained);

        /**double entropy = 0.0;
         double sentenceNb = 0.0;

         for (Iterable<String> sentence : sentences) {
         entropy += crossEntropy(sentence);
         sentenceNb++;
         }

         return entropy / sentenceNb;*/
        float words = 0;
        float[] wordEnts;
        int wordCount = 0;

        for (Iterable<String> senti : sentences) {
            String[] sent;
            if (senti != null) {
                sent = iterableToArray(senti);
            } else {
                sent = new String[0];
            }
            wordCount += sent.length;
            // calculate
            wordEnts = model.getWordEntropies(sent);
            words += MathUtils.sum(wordEnts);
        }
        // change from log10
        final float log2 = (float) Math.log10(2);
        words /= wordCount * log2 * -1;
        return words;

    }

    /**
     * @return 1 / i (as double)
     */
    private double invert(int i) {
        return 1.0 / (double) i;
    }

    private Iterable<String[]> iterablesToArrays(
            Iterable<Iterable<String>> iterable) {

        List<String[]> result = new LinkedList<>();

        for (Iterable<String> iterableElt : iterable) {
            result.add(iterableToArray(iterableElt));
        }


        return result;
    }


    private String[] iterableToArray(Iterable<String> iterable) {

        String[] result = new String[iterableSize(iterable)];
        if (iterable != null) {
            int i = 0;
            for (String s : iterable) {
                Assert.isTrue(i < result.length);

                result[i] = s;
                i++;
            }
        }
        return result;
    }

    private int iterableSize(Iterable iterable) {
        int result = 0;
        if (iterable != null) {
            for (Object o : iterable) {
                result++;
            }
        }

        return result;
    }

}
