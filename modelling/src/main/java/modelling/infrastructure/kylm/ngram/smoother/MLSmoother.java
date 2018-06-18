package modelling.infrastructure.kylm.ngram.smoother;


import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.NgramNode;

/**
 * Calculate N-gram maximum-likelihood probabilities (no smoothing)
 *
 * @author neubig
 */
public class MLSmoother extends NgramSmoother {

    public final static String ABV ="ml";


    /**
     * Serialization ID
     */
    private static final long serialVersionUID = -4731041681002702062L;

    @Override
    public void smooth(NgramLM lm) throws Exception {
        markTrimmed(lm);
        smoothRec(lm, lm.getRoot(), 0);
    }

    private void smoothRec(NgramLM lm, NgramNode node, int lev) {
        if (!node.hasChildren())
            return;
        this.trimNode(node, lev, lm.getNgramCounts());
        int count = 0;
        for (NgramNode child : node)
            count += child.getCount();
        float myLog = (float) Math.log10(count);
        for (NgramNode child : node) {
            smoothRec(lm, child, lev + 1);
            child.setScore((float) (Math.log10(child.getCount()) - myLog));
            node.setBackoffScore(Float.NEGATIVE_INFINITY);
        }
    }

    @Override
    public String getAbbr() {
        return "ml";
    }

    @Override
    public String getName() {
        return "Maximum Likelihood";
    }

}
