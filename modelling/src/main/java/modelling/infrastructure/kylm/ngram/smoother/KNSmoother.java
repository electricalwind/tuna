package modelling.infrastructure.kylm.ngram.smoother;


import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.NgramNode;

/**
 * Calculate N-gram probabilities with Kneser-Ney smoothing
 * @author neubig
 */
public class KNSmoother extends AbsoluteSmoother {

    public final static String ABV ="kn";


	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = -17904685317993557L;

	@Override
	public void smooth(NgramLM lm) throws Exception {



		// mark the values to be trimmed If they haven't already been
		if(cutoffs == null)
			cutoffs = new int[lm.getN()];
		markTrimmed(lm);



		// adjust the probability of each n-gram based on how
		//  many probabilities it occurs in
		NgramNode root = lm.getRoot();
		adjustCounts(root);
		root.setScore(0);

		super.smooth(lm);

	}

	// adjust the counts according to the Kneser-Ney criterion
	private void adjustCounts(NgramNode node) {
		if(node.getScore() == NgramNode.TRIM_SCORE)
			return;
		NgramNode myFallback = node.getFallback();
		if(myFallback != null) {
			// use the score being set to Double.MAX_VALUE as a sign that the count
			//  has already been reset. If it hasn't, reset to 0, then add one
			if(myFallback.getScore() != Float.MAX_VALUE) {
				myFallback.setScore( Float.MAX_VALUE );
				myFallback.setCount( 1 );
			}
			// otherwise, increment the count
			else
				myFallback.incrementCount();
		}
		// adjust for the children if they exist
		if(node.hasChildren())
			for(NgramNode child : node)
				adjustCounts(child);
	}

	public String getName() { return "Kneser-Ney"; }


	public String getAbbr() { return "kn"; }

}
