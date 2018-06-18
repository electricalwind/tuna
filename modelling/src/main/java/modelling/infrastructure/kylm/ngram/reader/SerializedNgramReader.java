package modelling.infrastructure.kylm.ngram.reader;

import modelling.infrastructure.kylm.ngram.NgramLM;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

/**
 * A class that can read an n-gram file in binary format
 * @author neubig
 *
 */
public class SerializedNgramReader extends NgramReader {

	@Override
	public NgramLM read(InputStream is) throws IOException {
		ObjectInputStream ois = new ObjectInputStream(is);
		try {
			return (NgramLM)ois.readObject();
		} catch(ClassNotFoundException e) {
			throw new IOException(e);
		}
	}

}
