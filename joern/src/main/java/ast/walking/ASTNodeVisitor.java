package ast.walking;

import ast.ASTNode;
import ast.declarations.ClassDefStatement;
import ast.expressions.*;
import ast.functionDef.FunctionDef;
import ast.functionDef.Parameter;
import ast.functionDef.ParameterList;
import ast.statements.*;

import java.util.Stack;


public class ASTNodeVisitor {
    protected Stack<Long> contextStack;


    public void visit(ASTNode item) {
        visitChildren(item);
    }

    public void visit(ParameterList item) {
        defaultHandler(item);
    }

    public void visit(Parameter item) {
        defaultHandler(item);
    }

    public void visit(FunctionDef item) {
        defaultHandler(item);
    }

    public void visit(ClassDefStatement item) {
        defaultHandler(item);
    }

    public void visit(IdentifierDeclStatement statementItem) {
        defaultHandler(statementItem);
    }

    public void visit(ExpressionStatement statementItem) {
        defaultHandler(statementItem);
    }

    public void visit(CallExpression expression) {
        defaultHandler(expression);
    }

    public void visit(Condition expression) {
        defaultHandler(expression);
    }

    public void visit(AssignmentExpr expression) {
        defaultHandler(expression);
    }

    public void visit(PrimaryExpression expression) {
        defaultHandler(expression);
    }

    public void visit(Identifier expression) {
        defaultHandler(expression);
    }

    public void visit(MemberAccess expression) {
        defaultHandler(expression);
    }

    public void visit(UnaryExpression expression) {
        defaultHandler(expression);
    }

    public void visit(Argument expression) {
        defaultHandler(expression);
    }

    public void visit(ReturnStatement expression) {
        defaultHandler(expression);
    }

    public void visit(GotoStatement expression) {
        defaultHandler(expression);
    }

    public void visit(ContinueStatement expression) {
        defaultHandler(expression);
    }

    public void visit(BreakStatement expression) {
        defaultHandler(expression);
    }

    public void visit(CompoundStatement expression) {
        defaultHandler(expression);
    }

    public void visit(IfStatement expression) {
        defaultHandler(expression);
    }

    public void visit(ForStatement expression) {
        defaultHandler(expression);
    }

    public void visit(WhileStatement expression) {
        defaultHandler(expression);
    }

    public void visit(DoStatement expression) {
        defaultHandler(expression);
    }

    public void visit(Label expression) {
        defaultHandler(expression);
    }

    public void visit(SwitchStatement expression) {
        defaultHandler(expression);
    }

    public void visit(TryStatement expression) {
        defaultHandler(expression);
    }

    public void visit(ThrowStatement expression) {
        defaultHandler(expression);
    }

    public void defaultHandler(ASTNode item) {
        // by default, redirect to visit(ASTNode item)
        visit(item);
    }

    public void visitChildren(ASTNode item) {
        int nChildren = item.getChildCount();

        for (int i = 0; i < nChildren; i++) {
            ASTNode child = item.getChild(i);
            child.accept(this);
        }

    }

}
