package modelling.infrastructure.kylm.ngram.smoother;

/**
 * Calculate N-gram probabilities with Kneser-Ney smoothing
 * @author neubig
 */
public class MKNSmoother extends KNSmoother {

    public final static String ABV ="mkn";


    private static final long serialVersionUID = 7680188047940465827L;
	private final static int FREQ_CUTOFF = 3;
	
	public MKNSmoother() {
	}
	
	/**
	 * Create a smoother with pre-set discounts
	 * @param discounts The discounts to use
	 */
	public MKNSmoother(float[][] discounts) {
		this.discounts = discounts;
	}
	
	protected float getDiscount(int order, int freq) {
		if (order >= ((float[][]) discounts).length) {
			// find the frequencies of frequencies
			final int[][] fofs = calcFofs(lm, 5);
			try {
				calcDiscounts(fofs);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		float ret = ((float[][])discounts)[order][(freq < FREQ_CUTOFF?freq:FREQ_CUTOFF)-1];
		//System.err.println("MKN.getDiscount("+order+","+freq+") = "+ret);
		return ret;
	}
	
	protected void calcDiscounts(int[][] fofs) throws Exception {
		float[][] newdisc = new float[fofs.length][FREQ_CUTOFF];
		for(int i = (smoothUnigrams?0:1); i < newdisc.length; i++) {
            float Y = 0;
            if(fofs[i][0] == 0) {
                throw new Exception("WARNING: no "+(i+1)+"-grams of frequency 1. Modified Kneser-Ney will not work. Try training with larger data, or use a different smoothing method.");
            } else if (fofs[i][1] == 0) {
                throw new Exception("WARNING: no "+(i+1)+"-grams of frequency 1. Modified Kneser-Ney will not work. Try training with larger data, or use a different smoothing method.");
            } else {
			    Y = fofs[i][0]*1.0f/(fofs[i][0]+2*fofs[i][1]);
            }
			for(int j = 0; j < FREQ_CUTOFF; j++) {
                float val = defaultDiscount;
                if(fofs[i][j+1] == 0) {
                    throw new Exception("WARNING: no "+(i+1)+"-grams of frequency "+(j+2)+". Modified Kneser-Ney will not work. Try training with larger data, or use a different smoothing method.");
                } else if (fofs[i][j] == 0) {
                    throw new Exception("WARNING: no "+(i+1)+"-grams of frequency "+(j+1)+". Modified Kneser-Ney will not work. Try training with larger data, or use a different smoothing method.");
                } else if (Y != 0) {
				    val = 1+j-(2+j)*Y*fofs[i][j+1]/fofs[i][j];
                    if(val < 0 || val > j+1)
                        throw new Exception("WARNING: illegal discount for n="+(j+1)+". Modified Kneser-Ney will not work. Try training with larger data, or use a different smoothing method.");
                }
				newdisc[i][j] = val;
			}
		}
		discounts = newdisc;
	}
	
	public String getName() { return "Modified Kneser-Ney"; }
	
	public  String getAbbr() { return "mkn"; }

}
