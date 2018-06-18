package tokenizer.file;

import tokenizer.file.java.strategy.BreadthFirstStrategy;
import tokenizer.file.java.strategy.DepthFirstStrategy;
import tokenizer.file.java.JavaAstTokenizer;
import tokenizer.file.java.JavaPrunedAstTokenizer;
import tokenizer.file.java.UTFWOCJavaTokenizer;
import tokenizer.file.java.xml.JavaLemmeTokenizer;
import tokenizer.file.java.xml.JavaLemmeWOCTokenizer;

/**
 * Factory for Java File Tokenizer
 */
public class JavaFileTokenizerFactory {

    public static AbstractFileTokenizer depthFirstTokenizer() {
        return new JavaAstTokenizer(new DepthFirstStrategy());
    }

    public static AbstractFileTokenizer breadthFirstTokenizer() {
        return new JavaAstTokenizer(new BreadthFirstStrategy());
    }

    public static AbstractFileTokenizer depthFirstPrunedTokenizer() {
        return new JavaPrunedAstTokenizer(new DepthFirstStrategy());
    }

    public static AbstractFileTokenizer breadthFirstPrunedTokenizer() {
        return new JavaPrunedAstTokenizer(new BreadthFirstStrategy());
    }

    public static AbstractFileTokenizer lemmeTokenizer() {
        return new JavaLemmeTokenizer();
    }

    public static AbstractFileTokenizer lemmeWocTokenizer() {
        return new JavaLemmeWOCTokenizer();
    }

    public static AbstractFileTokenizer utfTokenizer() {
        return new UTFFileTokenizer();
    }

    public static AbstractFileTokenizer utfWocTokenizer() {
        return new UTFWOCJavaTokenizer();
    }
}
