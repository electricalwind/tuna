package modelling.infrastructure.kylm.ngram.smoother;

import modelling.infrastructure.kylm.ngram.NgramLM;
import modelling.infrastructure.kylm.ngram.NgramNode;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * An abstract class that defines an algorithm to smooth an n-gram model
 * @author neubig
 *
 */
public abstract class NgramSmoother implements Serializable {
	
	/**
	 * Serialization ID
	 */
	private static final long serialVersionUID = 111803599347294292L;
	
	protected boolean smoothUnigrams = false;
	protected int debug = 0;
	protected int[] cutoffs = null;
	protected boolean marked = false;
	
	/**
	 * Smooth all the n-gram probabilities in a language model according to
	 * the appropriate smoothing algorithm.
	 * @param lm The N-gram language model to smooth.
	 */
	public abstract void smooth(NgramLM lm) throws Exception;
	
	/**
	 * calculate the frequencies of frequencies
	 * @param fofLimit The highest frequency of frequencies to count for each level
	 * @return An array of arrays, first dimension representing n-gram level,
	 *  second representing frequency.
	 */
	protected int[][] calcFofs (NgramLM lm, int fofLimit) {
		int[][] ret = new int[lm.getN()][fofLimit];
		addFofs(lm.getRoot(), ret, 0, fofLimit);
		return ret;
	}
	
	private void addFofs(NgramNode node, int[][] fofs, int currN, int fofLimit) {
		if(!node.hasChildren())
			return;
		for(NgramNode child : node) {
			if(child.getCount()!=0 && child.getCount() <= fofLimit)
				fofs[currN][child.getCount()-1]++;
			addFofs(child, fofs, currN+1, fofLimit);
		}
	}

	public void setSmoothUnigrams(boolean smoothUnigram) {
		this.smoothUnigrams = smoothUnigram;
	}

	public boolean getSmoothUnigrams() {
		return smoothUnigrams;
	}
	
	///////////////////////////////
	// methods for serialization //
	///////////////////////////////
	protected void writeObject(ObjectOutputStream out) throws IOException {
		out.writeBoolean(smoothUnigrams);
		out.writeObject(cutoffs);
	}
	protected void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		smoothUnigrams = in.readBoolean();
		cutoffs = (int[])in.readObject();
	}
	
	///////////////////
	// equals method //
	///////////////////
	public boolean equals(Object obj) {
		try {
			NgramSmoother ns = (NgramSmoother)obj;
			return
				ns.getClass().equals(getClass()) &&
				smoothUnigrams == ns.smoothUnigrams &&
				Arrays.equals(cutoffs, ns.cutoffs);
		} catch (Exception e) {	}
		return false;
	}
	
	// mark the nodes to be trimmed
	protected void markTrimmed(NgramLM lm) {
		if(cutoffs == null)
			return;
		if(!marked)
			markTrimmed(lm.getRoot(), 0);
		marked = true;
	}
	private void markTrimmed(NgramNode node, int lev) {
		if(!node.hasChildren())
			return;
		for(NgramNode child : node) {
			if(child.getCount() <= cutoffs[lev])
				child.setScore(NgramNode.TRIM_SCORE);
			else
				markTrimmed(child, lev+1);
		}
	}
	
	// trim the nodes that have been marked for trimming
	protected void trimNode(NgramNode node, int lev, int[] counts) {
		trimNode(node, lev, counts, 0);
	}
	protected void trimNode(NgramNode node, int lev, int[] counts, int good) {
		if(good == node.getChildCount())
			return;
		List<NgramNode> children = new ArrayList<>(good);
		for(NgramNode child : node) {
			if(child.getScore() != NgramNode.TRIM_SCORE)
				children.add(child);
			else {
				counts[lev]--;
				trimCounts(child, lev+1, counts);
			}
		}
		node.setChildren(children);
	}
	private void trimCounts(NgramNode node, int lev, int[] counts) {
		if(!node.hasChildren())
			return;
		counts[lev] -= node.getChildCount();
		for(NgramNode child : node)
			trimCounts(child, lev+1, counts);
	}
	
	public int getDebugLevel() { return debug; }
	public void setDebugLevel(int debug) { this.debug = debug; }
	
	public int[] getCutoffs() {
		return cutoffs;
	}

	public void setCutoffs(int[] cutoffs) {
		this.cutoffs = cutoffs;
	}

	/**
	 * @return The name of this type of smoothing
	 */
	public abstract String getName();
	/**
	 * @return The abbreviation given to this type of smoothing
	 */
	public abstract String getAbbr();
	
}
