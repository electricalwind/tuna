package modelling.infrastructure.kylm.ngram.reader;


import modelling.infrastructure.kylm.ngram.NgramLM;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

public abstract class NgramReader {

    /**
     * Read a language model from a file
     *
     * @param fileName The file to read the model from
     * @return The model that has been read
     * @throws IOException If the file could not be written to
     */
    public NgramLM read(String fileName) throws IOException {
        // X.Yao, 2010-07-22, make it able to read gzip-compressed file
        FileInputStream fin = new FileInputStream(fileName);
        String file = fileName.toLowerCase();
        if (file.endsWith(".gz") || file.endsWith(".gzip"))
            return read(new GZIPInputStream(fin));
        else
            return read(fin);
    }

    /**
     * Read a language model from an input stream
     *
     * @param is The input stream to read the model from
     * @return The model that has been read
     * @throws IOException If the file could not be read from
     */
    public abstract NgramLM read(InputStream is) throws IOException;

}
