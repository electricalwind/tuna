package modelling.infrastructure.kylm.ngram.writer;

import modelling.infrastructure.kylm.ngram.NgramLM;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * A class to write language models to binary files
 * @author neubig
 *
 */
public class SerializedNgramWriter extends NgramWriter {

	@Override
	public void write(NgramLM lm, OutputStream os) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(os);
		oos.writeObject(lm);
		oos.flush();
	}

}
