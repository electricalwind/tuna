
package modelling.infrastructure.kylm.ngram.reader;


import modelling.util.TextUtils;

import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * Implementation of a loader that loads directly from a text array in memory
 * @author neubig
 *
 */
public class TextArraySentenceReader implements SentenceReader {
	
	// iterator implementation
	private class TASLIterator implements Iterator<String[]> {
		
		private int pos;
		private String[] sents = null;
		private String divider = null;
		
		public TASLIterator(String[] sents, String divider) {
			pos = 0;
			this.sents = sents;
			this.divider = divider;
		}
		
		@Override
		public boolean hasNext() {
			return pos != sents.length;
		}

		@Override
		public String[] next() {
			if(sents[pos].length() == 0)
				return new String[0];
			StringTokenizer st=new StringTokenizer(sents[pos++],divider);
			String[] ret = new String[st.countTokens()];
			for(int i = 0; i < ret.length; i++)
				ret[i] = st.nextToken();
			return ret;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove is not implemented");
		}
		
	}
	
	private String[] sents = null;
	private String divider = null;
	
	/**
	 * The constructor, saves the sentence array and uses a single space as the default
	 * divider 
	 * @param sents the sentence array
	 */
	public TextArraySentenceReader(String[] sents) {
		this.sents = sents;
		divider = TextUtils.whiteSpaceString;
	}
	
	/**
	 * The constructor, saves the sentence array and uses the passed in divider 
	 * @param sents the sentence arrays
	 */
	public TextArraySentenceReader(String[] sents, String divider) {
		this.sents = sents;
		this.divider = divider;
	}

	@Override
	public Iterator<String[]> iterator() {
		return new TASLIterator(sents, divider);
	}

	@Override
	public boolean supportsReset() {
		return true;
	}

}
