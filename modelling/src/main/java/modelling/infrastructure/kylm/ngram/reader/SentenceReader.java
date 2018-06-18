package modelling.infrastructure.kylm.ngram.reader;

import java.util.Iterator;

/**
 * An abstract class that allows the loading of sentences
 * @author neubig
 * TODO: Add a "close" function
 */
public interface SentenceReader extends Iterable<String[]> {

	@Override
	public abstract Iterator<String[]> iterator();
	
	/**
	 * Whether the sentence loader supports returning multiple iterators or not.
	 * If multiple iterators are not supported, input can only be read once.
	 * @return Whether multiple iterators can be returned.
	 */
	public abstract boolean supportsReset();

}
