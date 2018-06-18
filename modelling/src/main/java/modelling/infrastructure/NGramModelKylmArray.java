package modelling.infrastructure;

import modelling.NGramModelArray;
import modelling.exception.TrainingFailedException;
import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.util.MathUtils;
import modelling.util.assertion.Assert;

public class NGramModelKylmArray implements NGramModelArray {
    private final int size;
    private final NgramLM model;
    private boolean trained;

    public NGramModelKylmArray(int size, NgramSmoother smoother) {
        Assert.isTrue(size > 1);
        Assert.notNull(smoother);
        this.size = size;
        this.model = new NgramLM(size, smoother);
        trained = false;
    }


    public NGramModelKylmArray(int size, NgramSmoother smoother, int threshold) {
        Assert.isTrue(size > 1);
        Assert.isTrue(threshold > 0);
        Assert.notNull(smoother);

        this.size = size;
        trained = false;

        this.model = new NgramLM(size, smoother);
        this.model.setVocabFrequency(threshold);
    }


    @Override
    public void train(Iterable<String[]> documents) throws TrainingFailedException {
        try {
            this.model.trainModel(documents);
            trained = true;
        } catch (Exception ex) {
            throw new TrainingFailedException(ex);
        }
    }

    @Override
    public double crossEntropy(String[] document) {
        float words = 0;
        float[] wordEnts;
        int wordCount = 0;
        wordCount += document.length;
        wordEnts = model.getWordEntropies(document);
        words += MathUtils.sum(wordEnts);
        final float log2 = (float) Math.log10(2);
        words /= wordCount * log2 * -1;
        return words;
    }

    @Override
    public double averageCrossEntropy(Iterable<String[]> documents) {
        Assert.isTrue(trained);

        float words = 0;
        float[] wordEnts;
        int wordCount = 0;

        for (String[] sent : documents) {
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
}
