package tokenizer.file.java;

import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.type.PrimitiveType;
import tokenizer.file.java.strategy.TreeVisitorStrategy;

public class PrunedTokenizerVisitor extends TokenizerVisitor {
    
    public PrunedTokenizerVisitor(TreeVisitorStrategy strategy) {
        super(strategy);
    }
    
    @Override
    public void visit(BinaryExpr n, Void arg) {
        addToken(n.getOperator().asString());
    }
    
    @Override
    public void visit(BlockStmt n, Void arg) {
    }
    
    @Override
    public void visit(BooleanLiteralExpr n, Void arg) {
        addToken(Boolean.toString(n.getValue()));
    }
    
    @Override
    public void visit(CharLiteralExpr n, Void arg) {
        addToken(n.getValue());
    }

    @Override
    public void visit(DoubleLiteralExpr n, Void arg) {
        addToken(n.getValue());
    }

    @Override
    public void visit(EmptyStmt n, Void arg) {
    }

    @Override
    public void visit(EnclosedExpr n, Void arg) {
    }    
    
    @Override
    public void visit(ExpressionStmt n, Void arg) {
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
    }    
    
    @Override
    public void visit(IntegerLiteralExpr n, Void arg) {
        addToken(n.getValue());
    }    
    
    @Override
    public void visit(LongLiteralExpr n, Void arg) {
        addToken(n.getValue());
    }
    
    @Override
    public void visit(NameExpr n, Void arg) {
    }

    @Override
    public void visit(Name n, Void arg) {
        addToken(n.getIdentifier());
    }    
    
    @Override
    public void visit(Parameter n, Void arg) {
    }

    @Override
    public void visit(PrimitiveType n, Void arg) {
        addToken(n.toString());
    }

    @Override
    public void visit(SimpleName n, Void arg) {
        addToken(n.getIdentifier());
    }

    @Override
    public void visit(StringLiteralExpr n, Void arg) {
        addToken(n.getValue());
    }
    
    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
    }

    @Override
    public void visit(VariableDeclarator n, Void arg) {
    }
}
