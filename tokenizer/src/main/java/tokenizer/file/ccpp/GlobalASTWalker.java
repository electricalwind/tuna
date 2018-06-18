package tokenizer.file.ccpp;

import ast.ASTNode;
import ast.ASTNodeBuilder;
import ast.walking.ASTWalker;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class GlobalASTWalker extends ASTWalker {

    public List<ASTNode> codeItems;

    public GlobalASTWalker() {
        codeItems = new LinkedList<ASTNode>();
    }

    public void startOfUnit(ParserRuleContext ctx, String filename) {

    }

    public void endOfUnit(ParserRuleContext ctx, String filename) {

    }

    public void processItem(ASTNode item, Stack<ASTNodeBuilder> itemStack) {
        codeItems.add(item);
    }
}
