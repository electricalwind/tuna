package modelling.infrastructure.kylm.ngram.writer;

import modelling.infrastructure.kylm.ngram.NgramLM;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * An abstract class the defines the functions needed to write an n-gram
 * model to a file or output stream.
 *
 * @author neubig
 */
public abstract class NgramWriter {

    /**
     * Write the language model to a file.
     *
     * @param lm       The language model to write
     * @param fileName The file to write it to
     * @throws IOException If the file could not be written to
     */
    public void write(NgramLM lm, String fileName) throws IOException {
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(fileName), 1024);
        write(lm, os);
        os.close();
    }

    /**
     * Write the language model to a generic output stream
     *
     * @param lm The language model to write
     * @param os The output stream to write to
     * @throws IOException If there was an error during output
     */
    public abstract void write(NgramLM lm, OutputStream os) throws IOException;

}