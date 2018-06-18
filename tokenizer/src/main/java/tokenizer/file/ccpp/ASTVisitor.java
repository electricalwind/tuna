package tokenizer.file.ccpp;

import ast.ASTNode;
import ast.declarations.ClassDefStatement;
import ast.declarations.IdentifierDecl;
import ast.declarations.IdentifierDeclType;
import ast.expressions.*;
import ast.functionDef.*;
import ast.statements.*;

import java.util.ArrayList;
import java.util.List;

public class ASTVisitor {

    public static final List<String> whenASTNode(ASTNode astNode) {
        List<String> tokens = new ArrayList<>();

        if (astNode instanceof FunctionDef) {
            tokens.add("Function Definition ");
            tokens.addAll(whenASTNode(((FunctionDef) astNode).name));
            tokens.addAll(whenASTNode(((FunctionDef) astNode).getParameterList()));
            tokens.addAll(whenASTNode(((FunctionDef) astNode).getContent()));

        } else if (astNode instanceof ReturnType) {
            tokens.add("ReturnType ");
            tokens.addAll(tillTheEnd(astNode));
        } else if (astNode instanceof Parameter) {
            tokens.add("Parameter ");
            tokens.addAll(whenASTNode(((Parameter) astNode).type));
            tokens.addAll(whenASTNode(((Parameter) astNode).name));
        } else if (astNode instanceof ParameterType) {
            tokens.add("Parameter Type ");
            tokens.addAll(tillTheEnd(astNode));
        } else if (astNode instanceof IdentifierDecl) {
            tokens.add("Identifier Decl ");
            tokens.addAll(whenASTNode(((IdentifierDecl) astNode).getType()));
            tokens.addAll(whenASTNode(((IdentifierDecl) astNode).getName()));
        } else if (astNode instanceof IdentifierDeclType) {
            tokens.add("Identifier Decl Type ");
            tokens.addAll(tillTheEnd(astNode));
        } else if (astNode instanceof ParameterList) {
            tokens.add("Parameter List: ");
            ((ParameterList) astNode).getParameters().forEach(parameter -> tokens.addAll(whenASTNode(parameter)));
        } else if (astNode instanceof Expression) {
            tokens.add("Expression -> ");
            tokens.addAll(whenExpression((Expression) astNode));
        } else if (astNode instanceof Statement) {
            tokens.add("Statement ->");
            tokens.addAll(whenStatement((Statement) astNode));
        } else {
            tokens.addAll(tillTheEnd(astNode));
           // System.out.println("wat");
        }
        return tokens;
    }


    public static final List<String> whenExpression(Expression expression) {
        List<String> tokens = new ArrayList<>();
        if (expression == null) {
            return tokens;
        }
        String operator = expression.getOperator();
        if (operator.length() > 0) {
            tokens.add(operator);
        }
        if (expression instanceof PostfixExpression) {
            tokens.add("Postfix ");
            tokens.addAll(whenPostFixExpression((PostfixExpression) expression));
        } else if (expression instanceof BinaryExpression) {
            tokens.add("Binary Exp ");
            tokens.addAll(whenBinaryExpression((BinaryExpression) expression));
        } else if (expression instanceof ExpressionHolder) {
            tokens.add("Expression Holder ");
            tokens.addAll(whenExpressionHolder((ExpressionHolder) expression));
        } else if (expression instanceof CastTarget) {
            tokens.add("Cast Target ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof ConditionalExpression) {
            tokens.add("Conditional Expression ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof UnaryOperator) {
            tokens.add("Unary Operator ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof Identifier) {
            tokens.add("Identifier ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof IncDec) {
            tokens.add("IncDec ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof ForInit) {
            tokens.add("For init ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof SizeofOperand) {
            tokens.add("Size Of Operand ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof CastExpression) {
            tokens.add("CastExpression ");
            tokens.addAll(whenASTNode(((CastExpression) expression).getCastTarget()));
        } else if (expression instanceof UnaryExpression) {
            tokens.add("Unary Expression ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof UnaryOp) {
            tokens.add("Unary Op ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof Sizeof) {
            tokens.add("Size of ");
            tokens.addAll(tillTheEnd(expression));
        } else if (expression instanceof SizeofExpr) {
            tokens.add("Size of Exp");
            tokens.addAll(tillTheEnd(expression));
        } else {
            tokens.addAll(tillTheEnd(expression));
            //System.out.println("watE");
        }


        return tokens;
    }

    public static final List<String> whenBinaryExpression(BinaryExpression binary) {
        List<String> tokens = new ArrayList<>();

        if (binary instanceof InclusiveOrExpression) {
            tokens.add("InclusiveOrExpression ");
        } else if (binary instanceof EqualityExpression) {
            tokens.add("Equality Expression ");
        } else if (binary instanceof RelationalExpression) {
            tokens.add("Relational Expression ");
        } else if (binary instanceof AndExpression) {
            tokens.add("And Expression ");
        } else if (binary instanceof MultiplicativeExpression) {
            tokens.add("Multiplicative Expression ");
        } else if (binary instanceof AssignmentExpr) {
            tokens.add("Assignement Expression ");
        } else if (binary instanceof AdditiveExpression) {
            tokens.add("Additive Expression ");
        } else if (binary instanceof ShiftExpression) {
            tokens.add("Shift Expression ");
        } else if (binary instanceof BitAndExpression) {
            tokens.add("Bit and Expression ");
        } else if (binary instanceof ExclusiveOrExpression) {
            tokens.add("Exclusive Or Expression ");
        } else if (binary instanceof OrExpression) {
            tokens.add("Or Expression ");
        } else {
           // System.out.println("watB");
        }
        //tokens.add(binarytokens.addAll(tillTheEnd());

        tokens.add("left ");
        tokens.addAll(whenExpression(binary.getLeft()));
        tokens.add("right ");
        tokens.addAll(whenExpression(binary.getRight()));

        return tokens;
    }

    public static final List<String> whenExpressionHolder(ExpressionHolder holder) {
        List<String> tokens = new ArrayList<>();
        if (holder instanceof Argument) {
            tokens.add("Argument ");
        } else if (holder instanceof Condition) {
            tokens.add("Condition ");
        } else if (holder instanceof InitializerList) {
            tokens.add("Initializer List ");
        } else if (holder instanceof Callee) {
            tokens.add("Callee ");
        } else if (holder instanceof ArgumentList) {
            tokens.add("Argument List");
        } else {
            //System.out.println("watH");
        }
        //tokens.add(holdertokens.addAll(tillTheEnd());

        tokens.addAll(whenExpression(holder.getExpression()));
        return tokens;
    }

    public static final List<String> whenPostFixExpression(PostfixExpression postfix) {
        List<String> tokens = new ArrayList<>();
        if (postfix instanceof IncDecOp) {
            tokens.add("Inc Dec op ");
            tokens.addAll(tillTheEnd(postfix));
        } else if (postfix instanceof PrimaryExpression) {
            tokens.add("Primary Expr");
            tokens.addAll(tillTheEnd(postfix));
        } else if (postfix instanceof PtrMemberAccess) {
            tokens.add("PtrMember Access");
            //TODO check
            tokens.addAll(tillTheEnd(postfix));
        } else if (postfix instanceof MemberAccess) {
            tokens.add("Member Access");
            tokens.addAll(tillTheEnd(postfix));
        } else if (postfix instanceof CallExpression) {
            tokens.add("Call Expresion: ");
            tokens.addAll(whenExpression(((CallExpression) postfix).getTarget()));

            if (postfix.getChildCount() > 1) {
                tokens.addAll(whenASTNode(postfix.getChild(1)));
            }
        } else {
            //System.out.println("watP");
            tokens.addAll(tillTheEnd(postfix));
        }
        return tokens;
    }


    public static final List<String> whenStatement(Statement statement) {
        List<String> tokens = new ArrayList<>();
        if (statement instanceof CompoundStatement) {
            tokens.add("Compound ");
            ((CompoundStatement) statement).getStatements().forEach(astNode -> tokens.addAll(whenASTNode(astNode)));
        } else if (statement instanceof ClassDefStatement) {
            tokens.add("Class Def ");
            tokens.addAll(whenASTNode(((ClassDefStatement) statement).name));
            tokens.addAll(whenASTNode(((ClassDefStatement) statement).content));
        } else if (statement instanceof BlockStarter) {
            tokens.add("Block Starter ");
            tokens.addAll(whenBlockStarter((BlockStarter) statement));
        } else if (statement instanceof Label) {
            tokens.add("Label ");
            tokens.addAll(tillTheEnd(statement));
        } else if (statement instanceof IdentifierDeclStatement) {
            tokens.add("Identifier Decl stat ");
            ((IdentifierDeclStatement) statement).getIdentifierDeclList().forEach(astNode -> tokens.addAll(whenASTNode(astNode)));
        } else if (statement instanceof ExpressionHolderStatement) {
            tokens.add("Expression Holder ");
            tokens.addAll(whenExpressionHolderStatement((ExpressionHolderStatement) statement));
        } else if (statement instanceof JumpStatement) {
            tokens.add("Jump ");
            tokens.addAll(whenJumpStatement((JumpStatement) statement));
        } else if (statement instanceof BlockCloser) {
            tokens.add("Block Closer ");
            tokens.addAll(tillTheEnd(statement));
        } else {
           //System.out.println("watS");
            tokens.addAll(tillTheEnd(statement));
        }
        return tokens;
    }

    public static final List<String> whenJumpStatement(JumpStatement jump) {
        List<String> tokens = new ArrayList<>();
        String var3;
        if (jump instanceof ThrowStatement) {
            tokens.add("Throw Statement");
        } else if (jump instanceof ReturnStatement) {
            tokens.add("Return Statement");
        } else if (jump instanceof BreakStatement) {
            tokens.add("Break Statement");
        } else if (jump instanceof ContinueStatement) {
            tokens.add("Continue Statement");
        } else if (jump instanceof GotoStatement) {
            tokens.add("Goto Statement");
        }
        tokens.addAll(tillTheEnd(jump));
        return tokens;
    }

    public static final List<String> whenExpressionHolderStatement(ExpressionHolderStatement holderstat) {
        List<String> tokens = new ArrayList<>();
        if (holderstat instanceof ExpressionStatement) {
            tokens.add("Expression Statement");
        }


        //TODO check
        if (holderstat.getExpression() != null) {
            tokens.addAll(whenExpression(holderstat.getExpression()));
        } else {
            tokens.addAll(tillTheEnd(holderstat));
        }

        return tokens;
    }

    public static final List<String> whenBlockStarter(BlockStarter blockstart) {
        List<String> tokens = new ArrayList<>();

        if (blockstart.getCondition() != null) {
            tokens.add("Condition: ");
            tokens.addAll(whenExpression(blockstart.getCondition()));
        }
        if (blockstart instanceof ForStatement) {
            tokens.add("For ");
            tokens.addAll(whenASTNode(((ForStatement) blockstart).getForInitStatement()));
            tokens.addAll(whenASTNode(((ForStatement) blockstart).getExpression()));
        } else if (blockstart instanceof WhileStatement) {
            tokens.add("While Statement ");
            tokens.addAll(tillTheEnd(blockstart));
        } else if (blockstart instanceof DoStatement) {
            tokens.add("Do Statement ");
            tokens.addAll(tillTheEnd(blockstart));
        } else if (blockstart instanceof TryStatement) {
            tokens.add("Try Statement ");
            ((TryStatement) blockstart).getCatchNodes().forEach(catchStatement -> tokens.addAll(whenBlockStarter(catchStatement)));
        } else if (blockstart instanceof CatchStatement) {
            tokens.add("Catch Statement ");
            tokens.addAll(tillTheEnd(blockstart));
        } else if (blockstart instanceof IfStatement) {
            tokens.add("If Statement ");
            if (((IfStatement) blockstart).getElseNode() != null)
                tokens.addAll(whenBlockStarter(((IfStatement) blockstart).getElseNode()));
        } else if (blockstart instanceof ElseStatement) {
            tokens.add("Else Statement ");
            tokens.addAll(tillTheEnd(blockstart));
        } else if (blockstart instanceof SwitchStatement) {
            tokens.add("Switch Statement");
            tokens.addAll(tillTheEnd(blockstart));
        } else {
            tokens.addAll(tillTheEnd(blockstart));
        }

        return tokens;
    }


    public static List<String> tillTheEnd(ASTNode node) {
        List<String> tokens = new ArrayList<>();
        if (node == null) return tokens;
        if (node.getChildCount() == 0) {
            tokens.add(node.getEscapedCodeStr());
        } else {
            for (int i = 0; i < node.getChildCount(); i++) {
                tokens.addAll(whenASTNode(node.getChild(i)));
            }
        }
        return tokens;
    }
}