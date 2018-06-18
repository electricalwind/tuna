package tokenizer.file.ccpp;

import antlr.C.ModuleLexer;
import ast.ASTNode;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Lexer;
import parsing.C.Modules.ANTLRCModuleParserDriver;
import parsing.TokenSubStream;
import tokenizer.file.AbstractFileTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.LinkedList;
import java.util.List;

import static tokenizer.file.ccpp.ASTVisitor.whenASTNode;

/**
 * Tokenizer relying on the joern project to build an ast and then visit it depth first
 * line are retrieved through the meta information of the tokens
 */
public class CPPASTFileTokenizer extends AbstractFileTokenizer {

    public final static String TYPE = "CPPASTFileTokenizer";

    @Override
    public Iterable<String> tokenize(Reader reader) throws IOException {
        ANTLRCModuleParserDriver parser = new ANTLRCModuleParserDriver();
        GlobalASTWalker walker = new GlobalASTWalker();
        parser.addObserver(walker);

        ANTLRInputStream inputStream = new ANTLRInputStream(reader);
        Lexer lexer = new ModuleLexer(inputStream);
        TokenSubStream token = new TokenSubStream(lexer);
        parser.parseAndWalkTokenStream(token);
        List<ASTNode> nodes = walker.codeItems;
        List<String> tokens = new LinkedList<>();
        for (int i = 0; i < nodes.size(); i++) {
            tokens.addAll(whenASTNode(nodes.get(i)));
        }
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }
}
