package modelling.infrastructure.kylm.ngram.reader;

import modelling.infrastructure.kylm.ClassMap;
import modelling.util.SymbolSet;

import java.io.IOException;


/**
 * A loader for a map file for class-based models
 * @author neubig
 *
 */
public interface ClassMapReader {
	
	ClassMap readClassMap(SymbolSet vocab, int fixed, boolean hasNames) throws IOException;

	
}
