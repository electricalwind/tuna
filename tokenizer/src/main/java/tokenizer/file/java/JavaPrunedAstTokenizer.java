package tokenizer.file.java;

import com.github.javaparser.ParseProblemException;
import tokenizer.file.java.strategy.TreeVisitorStrategy;
import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.Node;
import tokenizer.file.AbstractFileTokenizer;

import java.io.IOException;
import java.io.Reader;

/**
 * Stateless
 */
public class JavaPrunedAstTokenizer extends AbstractFileTokenizer {

    private final TreeVisitorStrategy strategy;

    public JavaPrunedAstTokenizer(TreeVisitorStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public Iterable<String> tokenize(Reader reader) {
        try {
            Node ast = JavaParser.parse(reader);

            PrunedTokenizerVisitor visitor =
                    new PrunedTokenizerVisitor(strategy);
            visitor.startVisit(ast);

            return visitor.tokens();
        } catch (ParseProblemException e) {
            return null;
        }

    }

    @Override
    public String getType() {
        return "Java" + "," 
                + "AST (pruned)" + "," 
                + strategy.toString();
    }
}
