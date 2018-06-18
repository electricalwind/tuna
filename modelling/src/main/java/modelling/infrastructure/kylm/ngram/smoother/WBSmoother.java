package modelling.infrastructure.kylm.ngram.smoother;


import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.NgramNode;
import modelling.util.MathUtils;

/**
 * Calculate N-gram probabilities with Witten-Bell smoothing
 *
 * @author neubig
 */
public class WBSmoother extends NgramSmoother {

    public final static String ABV ="wb";


    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -6764812467153305638L;
    private NgramLM lm = null;

    /**
     * Create a smoother and estimate the discounts automatically when it comes
     * time to smooth
     */
    public WBSmoother() {
    }

    @Override
    public void smooth(NgramLM lm) throws Exception {

        this.lm = lm;
        // mark the values to be trimmed
        markTrimmed(lm);

        // get the scores
        for (int i = 0; i < lm.getN(); i++)
            process(lm.getRoot(), 0, i);

        lm.getRoot().setBackoffScore(Float.NEGATIVE_INFINITY);

    }

    private void process(NgramNode node, int i, int n) {
        if (!node.hasChildren())
            return;
        // if this is not the level to be processed, move on
        if (i < n) {
            for (NgramNode child : node)
                process(child, i + 1, n);
            return;
        }
        // number of children
        final int numChildren = node.getChildCount();
        double tempScore;
        // calculate the backoff and scores
        float backoffScore = (n == 0 && !smoothUnigrams ? 0 : numChildren / (float) (numChildren + node.getCount()));
        final double otherScore = (n == 0 && !smoothUnigrams ? 1 : node.getCount() / (float) (numChildren + node.getCount()));
        int good = 0;
        for (NgramNode child : node) {
            tempScore = otherScore * child.getCount() / node.getCount();
            // if ok, add it
            if (child.getScore() != NgramNode.TRIM_SCORE) {
                child.setScore((float) Math.log10(tempScore));
                good++;
            } else
                backoffScore += tempScore;
        }
        backoffScore = (float) Math.log10(backoffScore);
        node.setBackoffScore(backoffScore);
        trimNode(node, i, lm.getNgramCounts(), good);
        // interpolate
        if (i != 0) {
            for (NgramNode child : node)
                child.setScore(MathUtils.logAddition(child.getScore(), backoffScore + child.getFallback().getScore()));
        } else if (smoothUnigrams) {
            // TODO: This should be distributed evenly between all unknown models
            NgramNode unk = node.getChild(2, (lm.getN() > 1 ? NgramNode.ADD_BRANCH : NgramNode.ADD_LEAF));
            if (unk.getScore() != 0.0f)
                unk.setScore(MathUtils.logAddition(unk.getScore(), backoffScore));
            else {
                unk.setScore(backoffScore);
                lm.getNgramCounts()[0]++;
            }
        }
    }

    @Override
    public String getAbbr() {
        return "wb";
    }

    @Override
    public String getName() {
        return "Witten-Bell";
    }

}
