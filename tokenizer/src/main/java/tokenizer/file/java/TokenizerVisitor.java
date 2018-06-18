package tokenizer.file.java;

import tokenizer.file.java.strategy.TreeVisitorStrategy;
import com.github.javaparser.ast.ArrayCreationLevel;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.PackageDeclaration;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.TypeExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.modules.ModuleExportsStmt;
import com.github.javaparser.ast.modules.ModuleOpensStmt;
import com.github.javaparser.ast.modules.ModuleProvidesStmt;
import com.github.javaparser.ast.modules.ModuleRequiresStmt;
import com.github.javaparser.ast.modules.ModuleUsesStmt;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.CatchClause;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.ForeachStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.SwitchEntryStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.SynchronizedStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.UnparsableStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.UnknownType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import util.Assert;

public class TokenizerVisitor extends VoidVisitorAdapter<Void> {

    private static final Logger LOG = Logger.getLogger(
            TokenizerVisitor.class.getName());
    
    private final TreeVisitorStrategy strategy;
    
    private final List<Node> queue = new LinkedList<>();
    private final List<String> tokens = new LinkedList<>();
    
    public TokenizerVisitor(TreeVisitorStrategy strategy) {
        Assert.notNull(strategy);
        LOG.setLevel(Level.SEVERE);
        this.strategy = strategy;
    }
    
    public Iterable<String> tokens() {
        return tokens;
    }
    
    public void startVisit(Node n) {
        Assert.notNull(n);
        
        queue.add(n);
        while (!queue.isEmpty()) {
            visitNext();
        }
    }
    
    private void visitNext() {
        Assert.notEmpty(queue);
        
        Node next = queue.remove(0);
        next.accept(this, null);
        LOG.log(Level.INFO, "Added token: "
                + tokens.get(tokens.size() - 1)
                + " ("
                + next.getChildNodes().size()
                + " children)"
        );
        strategy.addChildrenToQueue(queue, next.getChildNodes());
    }
    
    void addToken(String token) {
        Assert.notNull(token);
        
        tokens.add(token);
    }
    
    @Override
    public void visit(NodeList n, Void arg) {
       Assert.shouldNeverGetHere();
       n.forEach(node -> queue.add((Node) node));
       visitNext();
    }

    @Override
    public void visit(AnnotationDeclaration n, Void arg) {
        addToken("AnnotationDeclaration");
    }

    @Override
    public void visit(AnnotationMemberDeclaration n, Void arg) {
        addToken("AnnotationMemberDeclaration");
    }

    @Override
    public void visit(ArrayAccessExpr n, Void arg) {
        addToken("ArrayAccessExpr");
    }

    @Override
    public void visit(ArrayCreationExpr n, Void arg) {        
        addToken("ArrayCreationExpr");
    }

    @Override
    public void visit(ArrayCreationLevel n, Void arg) {
        addToken("ArrayCreationExpr");
        //Assert.isTrue(n.getChildNodes().size() <= 1);
    }

    @Override
    public void visit(ArrayInitializerExpr n, Void arg) {
        addToken("ArrayInitializeExpr");
    }

    @Override
    public void visit(ArrayType n, Void arg) {
        addToken("ArrayType");
    }

    @Override
    public void visit(AssertStmt n, Void arg) {
        addToken("AssertStmt");
    }

    @Override
    public void visit(AssignExpr n, Void arg) {
        addToken("AssignExpr");
    }

    @Override
    public void visit(BinaryExpr n, Void arg) {
        addToken("BinaryExpr");
        addToken(n.getOperator().asString());
    }

    @Override
    public void visit(BlockComment n, Void arg) {
        // Skip comments
    }

    @Override
    public void visit(BlockStmt n, Void arg) {
        addToken("BlockStmt");
    }

    @Override
    public void visit(BooleanLiteralExpr n, Void arg) {
        addToken("BooleanLiteralExpr");
        addToken(Boolean.toString(n.getValue()));
    }

    @Override
    public void visit(BreakStmt n, Void arg) {
        addToken("BreakStmt");
    }

    @Override
    public void visit(CastExpr n, Void arg) {
        addToken("CastExpr");
    }

    @Override
    public void visit(CatchClause n, Void arg) {
        addToken("CatchClause");
    }

    @Override
    public void visit(CharLiteralExpr n, Void arg) {
        addToken("CharLiteralExpr");
        addToken(n.getValue());
    }

    @Override
    public void visit(ClassExpr n, Void arg) {
        addToken("ClassExpr");
    }

    @Override
    public void visit(ClassOrInterfaceDeclaration n, Void arg) {
        addToken("ClassOrInterfaceDeclaration");
    }

    @Override
    public void visit(ClassOrInterfaceType n, Void arg) {
        addToken("ClassOrInterfaceType");
    }

    @Override
    public void visit(CompilationUnit n, Void arg) {
        addToken("CompilationUnit");
    }

    @Override
    public void visit(ConditionalExpr n, Void arg) {
        addToken("ConditionalExpr");
    }

    @Override
    public void visit(ConstructorDeclaration n, Void arg) {
        addToken("ConstructorDeclaration");
    }

    @Override
    public void visit(ContinueStmt n, Void arg) {
        addToken("ContinueStmt");
    }

    @Override
    public void visit(DoStmt n, Void arg) {
        addToken("DoStmt");
    }

    @Override
    public void visit(DoubleLiteralExpr n, Void arg) {
        addToken("DoubleLiteralExpr");
        addToken(n.getValue());
    }

    @Override
    public void visit(EmptyStmt n, Void arg) {
        addToken("EmptyStmt");
    }

    @Override
    public void visit(EnclosedExpr n, Void arg) {
        addToken("EnclosedExpr");
    }

    @Override
    public void visit(EnumConstantDeclaration n, Void arg) {
        addToken("EnumConstatDeclaration");
    }

    @Override
    public void visit(EnumDeclaration n, Void arg) {
        addToken("EnumDeclaration");
    }

    @Override
    public void visit(ExplicitConstructorInvocationStmt n, Void arg) {
        if (n.isThis()) {
            addToken("this");
        } else {
            if (n.toString().contains("super")) {
                addToken("this");
            } else {
                System.out.println(n.toString());
                Assert.shouldNeverGetHere();
            }
        }
    }

    @Override
    public void visit(ExpressionStmt n, Void arg) {
        addToken("ExpressionStmt");
    }

    @Override
    public void visit(FieldAccessExpr n, Void arg) {
        addToken("FieldAccessExpr");
    }

    @Override
    public void visit(FieldDeclaration n, Void arg) {
        addToken("FieldDeclaration");
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        addToken("ForStmt");
    }

    @Override
    public void visit(ForeachStmt n, Void arg) {
        addToken("ForeachStmt");
    }

    @Override
    public void visit(IfStmt n, Void arg) {
        addToken("IfStmt");
    }

    @Override
    public void visit(ImportDeclaration n, Void arg) {
        addToken("ImportDeclaration");
    }

    @Override
    public void visit(InitializerDeclaration n, Void arg) {
        addToken("InitializerDeclaration");
    }

    @Override
    public void visit(InstanceOfExpr n, Void arg) {
        addToken("InstanceOfExpr");
    }

    @Override
    public void visit(IntegerLiteralExpr n, Void arg) {
        addToken("IntegerLiteralExpr");
        addToken(n.getValue());
    }

    @Override
    public void visit(IntersectionType n, Void arg) {
        System.out.println("IntersectionType("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(JavadocComment n, Void arg) {
        // Skip java doc
    }

    @Override
    public void visit(LabeledStmt n, Void arg) {
        addToken("LabeledStmt");
    }

    @Override
    public void visit(LambdaExpr n, Void arg) {
        addToken("LambdaExpr");
    }

    @Override
    public void visit(LineComment n, Void arg) {
        // Skip comments
    }

    @Override
    public void visit(LocalClassDeclarationStmt n, Void arg) {
        addToken("LoadClassDeclarationStmt");
    }

    @Override
    public void visit(LongLiteralExpr n, Void arg) {
        addToken("LongLiteralExpr");
        addToken(n.getValue());
    }

    @Override
    public void visit(MarkerAnnotationExpr n, Void arg) {
        addToken("MarkerAnnotationExpr");
    }

    @Override
    public void visit(MemberValuePair n, Void arg) {
        addToken("MemberValuePair");
    }

    @Override
    public void visit(MethodCallExpr n, Void arg) {
        addToken("MethodCallExpr");
    }

    @Override
    public void visit(MethodDeclaration n, Void arg) {
        addToken("MethodDeclaration");
    }

    @Override
    public void visit(MethodReferenceExpr n, Void arg) {
        addToken("MethodDeclaration");
        addToken(n.getIdentifier());
    }

    @Override
    public void visit(NameExpr n, Void arg) {
        Assert.isTrue(n.getChildNodes().size() == 1);
        addToken("NameExpr");
    }

    @Override
    public void visit(Name n, Void arg) {
        addToken("Name");
        addToken(n.getIdentifier());
    }

    @Override
    public void visit(NormalAnnotationExpr n, Void arg) {
        addToken("NormalAnnotationExpr");
    }

    @Override
    public void visit(NullLiteralExpr n, Void arg) {
        addToken("NullLiteralExpr");
    }

    @Override
    public void visit(ObjectCreationExpr n, Void arg) {
        addToken("ObjectCreationExpr");
    }

    @Override
    public void visit(PackageDeclaration n, Void arg) {
        addToken("PackageDeclaration");
    }

    @Override
    public void visit(Parameter n, Void arg) {
        addToken("Parameter");
    }

    @Override
    public void visit(PrimitiveType n, Void arg) {
        addToken("PrimitiveType");
        addToken(n.toString());
    }

    @Override
    public void visit(ReturnStmt n, Void arg) {
        addToken("ReturnStmt");
    }

    @Override
    public void visit(SimpleName n, Void arg) {
        addToken("SimpleName");
        addToken(n.getIdentifier());
    }

    @Override
    public void visit(SingleMemberAnnotationExpr n, Void arg) {
        addToken("SingleMemberAnnotationExpr");
    }

    @Override
    public void visit(StringLiteralExpr n, Void arg) {
        addToken("StringLiteralExpr");
        addToken(n.getValue());
    }

    @Override
    public void visit(SuperExpr n, Void arg) {
        addToken("SuperExpr");
    }

    @Override
    public void visit(SwitchEntryStmt n, Void arg) {
        addToken("SwitchEntryStmt");
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {        
        addToken("SwitchStmt");
    }

    @Override
    public void visit(SynchronizedStmt n, Void arg) {
        addToken("SynchronizedStmt");
    }

    @Override
    public void visit(ThisExpr n, Void arg) {
        addToken("ThisExpr");
    }

    @Override
    public void visit(ThrowStmt n, Void arg) {
        addToken("ThrowStmt");
    }

    @Override
    public void visit(TryStmt n, Void arg) {
        addToken("TryStmt");
    }

    @Override
    public void visit(TypeExpr n, Void arg) {
        addToken("TypeExpr");
    }

    @Override
    public void visit(TypeParameter n, Void arg) {
        addToken("TypeParameter");
    }

    @Override
    public void visit(UnaryExpr n, Void arg) {
        addToken("UnaryExpr");
        addToken(n.getOperator().asString());
    }

    @Override
    public void visit(UnionType n, Void arg) {
        addToken("UnionType");
    }

    @Override
    public void visit(UnknownType n, Void arg) {
        addToken("UnknownType");
    }

    @Override
    public void visit(VariableDeclarationExpr n, Void arg) {
        addToken("VariableDeclarationExpr");
    }

    @Override
    public void visit(VariableDeclarator n, Void arg) {
        addToken("VariableDeclarator");
    }

    @Override
    public void visit(VoidType n, Void arg) {
        addToken("VoidType");
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        addToken("WhileStmt");
    }

    @Override
    public void visit(WildcardType n, Void arg) {
        addToken("WildCardType");
    }

    @Override
    public void visit(ModuleDeclaration n, Void arg) {
        System.out.println("ModuleDeclaration("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(ModuleRequiresStmt n, Void arg) {
        System.out.println("ModuleRequiresStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(ModuleExportsStmt n, Void arg) {
        System.out.println("ModuleExportsStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(ModuleProvidesStmt n, Void arg) {
        System.out.println("ModuleProvidesStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(ModuleUsesStmt n, Void arg) {
        System.out.println("ModuleUsesStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(ModuleOpensStmt n, Void arg) {
        System.out.println("ModuleOpensStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void visit(UnparsableStmt n, Void arg) {
        System.out.println("UnparsableStmt("+n.getChildNodes().size()+" children): "+n.toString());
        
        n.getChildNodes().forEach(child -> System.out.println(child.toString()));
        throw new UnsupportedOperationException("Not implemented yet");
    }   
}