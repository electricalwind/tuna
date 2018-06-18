package defectstudy.callable;

import modelling.util.Pair;
import tokenizer.file.AbstractFileTokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.Callable;

public class TokenizeF implements Callable<Pair<String, Iterable<String>>> {
    private final String pathToRemove;
    private String path;
    private AbstractFileTokenizer tokenizer;

    public TokenizeF(String path, AbstractFileTokenizer tokenizer, String pathToRemove) {
        this.path = path;
        this.tokenizer = tokenizer;
        this.pathToRemove = pathToRemove;
    }

    @Override
    public Pair<String, Iterable<String>> call() throws Exception {
        Reader reader = new FileReader(new File(path));
        return new Pair<>(path.replace(pathToRemove, ""), tokenizer.tokenize(reader));

    }


}
