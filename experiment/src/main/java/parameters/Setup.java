package parameters;

import modelling.infrastructure.KylmSmootherFactory;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;
import modelling.util.assertion.Assert;

import static warzone.experiment.util.Separators.COLUMN_SEPARATOR;

@SuppressWarnings("Duplicates")
public class Setup {
    private static final int DEFAULT_NGRAM_SIZE = 3;
    private static final NgramSmoother DEFAULT_SMOOTHER
            = KylmSmootherFactory.maximumLikelihood();
    private static final int DEFAULT_THRESHOLD = 1;


    private final int nGramSize;
    private final NgramSmoother smoother;
    private final int unknown;

    public Setup() {
        nGramSize = DEFAULT_NGRAM_SIZE;
        smoother = DEFAULT_SMOOTHER;
        unknown = DEFAULT_THRESHOLD;
    }

    public Setup(
            int size, NgramSmoother smoother, int unknown) {
        Assert.isTrue(size > 1);
        Assert.notNull(smoother);


        nGramSize = size;
        this.smoother = smoother;
        this.unknown = unknown;
    }

    /**
     * @return a new ExperimentSetup es with: es.size = size,
     * es.smoother = this.smoother and es.unknown = this.unknown.
     */
    public Setup newSetups(int size) {
        return new Setup(size, smoother, unknown);
    }

    /**
     * @return a new ExperimentSetup es with: es.size = this.nGramSize,
     * es.smoother = smoother and es.unknown = this.unknown.
     */
    public Setup newSetup(NgramSmoother smoother) {
        return new Setup(nGramSize, smoother, unknown);
    }

    /**
     * @return a new ExperimentSetup es with: es.size = this.nGramSize,
     * es.smoother = this.smoother and es.unknown = threshold.
     */
    public Setup newSetupt(int threshold) {
        return new Setup(nGramSize, smoother, threshold);
    }

    public int ngramSize() {
        return nGramSize;
    }

    public NgramSmoother smoother() {
        return smoother;
    }

    public int threshold() {
        return unknown;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(nGramSize);
        builder.append(COLUMN_SEPARATOR);

        builder.append(smoother.getName());
        builder.append(COLUMN_SEPARATOR);

        builder.append(unknown);

        return builder.toString();
    }
}
