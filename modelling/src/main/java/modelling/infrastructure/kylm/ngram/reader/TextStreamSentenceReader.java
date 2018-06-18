package modelling.infrastructure.kylm.ngram.reader;


import modelling.util.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.StringTokenizer;

/**
 * A sentence loader that loads from an arbitrary input stream
 * @author neubig
 *
 */
public class TextStreamSentenceReader implements SentenceReader {
	
	// iterator implementation
	private class TSSLIterator implements Iterator<String[]> {
		
		private BufferedReader br = null;
		private String divider = null;
		private String next = null;
		
		public TSSLIterator(InputStream is, String divider) {
			br = new BufferedReader(new InputStreamReader(is));
			this.divider = divider;
		}
		
		@Override
		public boolean hasNext() {
			try {
				if(next == null) next = br.readLine();
				return (next != null);
			} catch (IOException e) {
				return false;
			}
		}

		@Override
		public String[] next() {
			try {
				if(next == null) next = br.readLine();
				StringTokenizer st=new StringTokenizer(next,divider);
				String[] ret = new String[st.countTokens()];
				for(int i = 0; i < ret.length; i++)
					ret[i] = st.nextToken();
				next = null;
				return ret;
			} catch (IOException e) {
				throw new IllegalArgumentException(e);
			}
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove is not implemented");
		}
		
	}
	
	private InputStream is = null;
	private String divider = null;
	private boolean mark = false;
	
	/**
	 * The constructor, saves the sentence array and uses a single space as the default
	 * divider 
	 * @param is The text stream from which to load the sentences
	 */
	public TextStreamSentenceReader(InputStream is) {
		this.is = is;
		divider = TextUtils.whiteSpaceString;
	}
	
	/**
	 * The constructor, saves the sentence array and uses the passed in divider 
	 * @param is The text stream from which to load the sentences
	 * @param divider The divider that is used to divide the text strings
	 */
	public TextStreamSentenceReader(InputStream is, String divider) {
		this.is = is;
		this.divider = divider;
	}

	@Override
	public Iterator<String[]> iterator() {
		if(mark) 
			throw new UnsupportedOperationException("Only a single iterator can be returned for a stream loader");
		mark = true;
		return new TSSLIterator(is, divider);
	}

	@Override
	public boolean supportsReset() {
		return false;
	}

}
