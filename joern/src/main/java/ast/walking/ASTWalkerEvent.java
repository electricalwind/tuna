package ast.walking;

import ast.ASTNode;
import ast.ASTNodeBuilder;
import org.antlr.v4.runtime.ParserRuleContext;

import java.util.Stack;

public class ASTWalkerEvent {

    public enum eventID {
        BEGIN, START_OF_UNIT, END_OF_UNIT, PROCESS_ITEM, END
    }

    public ASTWalkerEvent(eventID aId) {
        id = aId;
    }

    public eventID id;
    public ParserRuleContext ctx;
    public String filename;
    public Stack<ASTNodeBuilder> itemStack;
    public ASTNode item;
}
