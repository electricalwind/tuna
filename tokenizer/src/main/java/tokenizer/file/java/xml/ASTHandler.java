package tokenizer.file.java.xml;

/**
 * public class ASTHandler {
 *
 * private static List<String> handlingAnnotations(NodeWithAnnotations node) {
 * List<String> tokens = new ArrayList<>();
 * //tokens.add("~Annotations");
 * node.getAnnotations().forEach(expr -> {
 * AnnotationExpr ann = (AnnotationExpr) expr;
 * tokens.add(ann.getName().asString());
 * });
 * return tokens;
 * }
 *
 * private static List<String> handlingArguments(NodeWithArguments node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Arguments");
 * node.getArguments().forEach(o -> tokens.addAll(printNode((Node) o)));
 * return tokens;
 * }
 *
 * private static List<String> handlingBlckStment(NodeWithBlockStmt node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Block");
 * node.getBody().getStatements().forEach(statement -> tokens.addAll(handlingStatement(statement)));
 * return tokens;
 * }
 *
 * private static List<String> handlingStatement(Statement statement) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Statement");
 * statement.getChildNodes().forEach(node -> tokens.addAll(printNode(node)));
 * return tokens;
 * }
 *
 * private static List<String> handlingCondition(NodeWithCondition node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Condition");
 * tokens.addAll(printNode(node.getCondition()));
 * return tokens;
 * }
 *
 * private static List<String> handlingDeclaration(NodeWithDeclaration node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Declaration");
 * tokens.add(node.getDeclarationAsString());
 * return tokens;
 * }
 *
 * private static List<String> handlingExpression(NodeWithExpression node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Expression");
 * tokens.addAll(printNode(node.getExpression()));
 * return tokens;
 * }
 *
 * private static List<String> handlingExtends(NodeWithExtends node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Extend");
 * node.getExtendedTypes().forEach(o -> {
 * ClassOrInterfaceType cl = (ClassOrInterfaceType) o;
 * tokens.addAll(handlingAnnotations(cl));
 * cl.getScope().ifPresent(classOrInterfaceType -> tokens.add(classOrInterfaceType.getName().getIdentifier()));
 * cl.getTypeArguments().ifPresent(types -> types.forEach(type -> tokens.add(type.asString())));
 * tokens.add(cl.getName().getIdentifier());
 *
 * });
 * return tokens;
 * }
 *
 * private static List<String> handlingImplements(NodeWithImplements node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Implement");
 * node.getImplementedTypes().forEach(o -> {
 * ClassOrInterfaceType cl = (ClassOrInterfaceType) o;
 * tokens.addAll(handlingAnnotations(cl));
 * cl.getScope().ifPresent(classOrInterfaceType -> tokens.add(classOrInterfaceType.getName().getIdentifier()));
 * cl.getTypeArguments().ifPresent(types -> types.forEach(type -> tokens.add(type.asString())));
 * tokens.add(cl.getName().getIdentifier());
 * });
 * return tokens;
 * }
 *
 * private static List<String> handlingMembers(NodeWithMembers node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Fields");
 * node.getFields().forEach(o -> {
 * FieldDeclaration declaration = (FieldDeclaration) o;
 * tokens.addAll(handlingAnnotations(declaration));
 * EnumSet<Modifier> modifiers = declaration.getModifiers();
 * modifiers.forEach(modifier -> tokens.add(modifier.asString()));
 * if (declaration.isTransient()) {
 * tokens.add("transient");
 * }
 * if (declaration.isVolatile()) {
 * tokens.add("volatile");
 * }
 * NodeList<VariableDeclarator> variables = declaration.getVariables();
 * variables.forEach(variableDeclarator -> {
 * tokens.add(variableDeclarator.getType().asString());
 * tokens.add(variableDeclarator.getName().getIdentifier());
 * variableDeclarator.getInitializer().ifPresent(expression -> {
 * tokens.add("initWith");
 * tokens.addAll(printNode(expression));
 * });
 * });
 * });
 * tokens.add("~Methods");
 * node.getMethods().forEach(o -> {
 * MethodDeclaration declaration = (MethodDeclaration) o;
 * tokens.addAll(handlingAnnotations(declaration));
 * EnumSet<Modifier> modifiers = declaration.getModifiers();
 * modifiers.forEach(modifier -> tokens.add(modifier.asString()));
 * tokens.add(declaration.getType().asString());
 * tokens.add(declaration.getName().getIdentifier());
 * tokens.add("Parameters");
 * declaration.getParameters().forEach(parameter -> tokens.addAll(printNode(parameter)));
 * tokens.add("Body");
 * declaration.getBody().ifPresent(blockStmt -> tokens.addAll(handlingStatement(blockStmt)));
 * });
 * return tokens;
 * }
 *
 * private static List<String> handlingParameter(NodeWithParameters node) {
 * List<String> tokens = new ArrayList<>();
 * tokens.add("~Parameters");
 * ((NodeWithParameters) node).getParameters().forEach(o -> {
 * Parameter parameter = (Parameter) o;
 * tokens.addAll(handlingAnnotations(parameter));
 *
 * EnumSet<Modifier> modifiers = parameter.getModifiers();
 * modifiers.forEach(modifier -> tokens.add(modifier.asString()));
 *
 * tokens.add(parameter.getType().asString());
 * tokens.add(parameter.getName().getIdentifier());
 * if (parameter.isVarArgs()) {
 * tokens.add("vararg");
 * }
 * });
 * return tokens;
 * }
 *
 *
 * public static List<String> printNode(Node node) {
 * List<String> tokens = new ArrayList<>();
 * boolean check = false;
 * boolean named = false;
 * tokens.add(node.getMetaModel().getTypeName());
 * if (node instanceof NodeWithAnnotations) {
 * tokens.addAll(handlingAnnotations((NodeWithAnnotations) node));
 * check = true;
 * }
 * if (node instanceof NodeWithArguments) {
 * tokens.addAll(handlingArguments((NodeWithArguments) node));
 * check = true;
 * }
 * if (node instanceof NodeWithBlockStmt) {
 * tokens.addAll(handlingBlckStment((NodeWithBlockStmt) node));
 * check = true;
 * }
 * if (node instanceof NodeWithBody) {
 * tokens.addAll(handlingStatement(((NodeWithBody) node).getBody()));
 * check = true;
 * }
 * if (node instanceof NodeWithCondition) {
 * tokens.addAll(handlingCondition((NodeWithCondition) node));
 * check = true;
 * }
 * if (node instanceof NodeWithDeclaration) {
 * tokens.addAll(handlingDeclaration((NodeWithDeclaration) node));
 * }
 * if (node instanceof NodeWithExpression) {
 * tokens.addAll(handlingExpression((NodeWithExpression) node));
 * check = true;
 * }
 * if (node instanceof NodeWithExtends) {
 * tokens.addAll(handlingExtends((NodeWithExtends) node));
 * check = true;
 * }
 * if (node instanceof NodeWithIdentifier) {
 * tokens.add(((NodeWithIdentifier) node).getIdentifier());
 * }
 * if (node instanceof NodeWithImplements) {
 * tokens.addAll(handlingExtends((NodeWithExtends) node));
 * check = true;
 * }
 * if (node instanceof NodeWithJavadoc) {
 * System.out.println("ignoring comments");
 * }
 * if (node instanceof NodeWithMembers) {
 * tokens.addAll(handlingMembers((NodeWithMembers) node));
 * check = true;
 * }
 * if (node instanceof NodeWithModifiers) {
 * EnumSet<Modifier> modifiers = ((NodeWithModifiers) node).getModifiers();
 * modifiers.forEach(o -> tokens.add(o.asString()));
 *
 * }
 * if (node instanceof NodeWithName) {
 * tokens.add(((NodeWithName) node).getName().asString());
 * check = true;
 * }
 * if (node instanceof NodeWithOptionalBlockStmt) {
 * ((NodeWithOptionalBlockStmt) node).getBody().ifPresent(o -> tokens.addAll(handlingStatement((Statement) o)));
 * System.out.println("wat");
 *
 * check = true;
 * }
 * if (node instanceof NodeWithOptionalLabel) {
 * ((NodeWithOptionalLabel) node).getLabel().ifPresent(o -> tokens.add(((SimpleName) o).asString()));
 * System.out.println("wat2");
 * check = true;
 * }
 * if (node instanceof NodeWithOptionalScope) {
 * ((NodeWithOptionalScope) node).getScope().ifPresent(o -> tokens.addAll(printNode((Node) o)));
 * System.out.println("wat3");
 * check = true;
 * }
 * if (node instanceof NodeWithParameters) {
 * tokens.addAll(handlingParameter((NodeWithParameters) node));
 * check = true;
 * }
 * if (node instanceof NodeWithSimpleName) {
 * tokens.add(((NodeWithSimpleName) node).getName().getIdentifier());
 * }
 * if (node instanceof NodeWithStatements) {
 * ((NodeWithStatements) node).getStatements().forEach(o -> tokens.addAll(handlingStatement((Statement) o)));
 * check = true;
 * }
 * if (node instanceof NodeWithThrownExceptions) {
 * tokens.add("~Throws");
 * ((NodeWithThrownExceptions) node).getThrownExceptions().forEach(o -> {
 * ReferenceType type = (ReferenceType) o;
 * type.getAnnotations().forEach(expr -> {
 * AnnotationExpr ann = expr;
 * tokens.add(ann.getName().getIdentifier());
 * });
 * tokens.add(type.asString());
 * });
 * check = true;
 * }
 * if (node instanceof NodeWithType) {
 * tokens.add(((NodeWithType) node).getType().asString());
 * }
 * if (node instanceof NodeWithTypeArguments) {
 * tokens.add("~Type Arguments");
 * if (((NodeWithTypeArguments) node).isUsingDiamondOperator()) {
 * tokens.add("Diamond Operator");
 * }
 * ((NodeWithTypeArguments) node).getTypeArguments().ifPresent(o -> ((NodeList<Type>) o).forEach(type -> tokens.add(type.asString())));
 * check = true;
 * }
 * if (node instanceof NodeWithTypeParameters) {
 * tokens.add("~Type Parameters");
 * if (((NodeWithTypeParameters) node).isGeneric()) {
 * tokens.add("generic");
 * }
 * ((NodeWithTypeParameters) node).getTypeParameters().forEach(o -> {
 * TypeParameter parameter = (TypeParameter) o;
 * parameter.getTypeBound().forEach(classOrInterfaceType -> tokens.add(classOrInterfaceType.asString()));
 * tokens.add(parameter.getName().getIdentifier());
 * });
 * check = true;
 * }
 * if (node instanceof NodeWithVariables) {
 * tokens.add("~Variables");
 * tokens.add(((NodeWithVariables) node).getElementType().asString());
 * ((NodeWithVariables) node).getVariables().forEach(o -> {
 * VariableDeclarator variableDeclarator = (VariableDeclarator) o;
 * tokens.add(variableDeclarator.getType().asString());
 * tokens.add(variableDeclarator.getName().getIdentifier());
 * variableDeclarator.getInitializer().ifPresent(expression -> {
 * tokens.add("initWith");
 * tokens.addAll(printNode(expression));
 * });
 * });
 * check = true;
 * }
 * if (!check) {
 * node.getChildNodes().forEach(o -> tokens.addAll(printNode((Node) o)));
 * }
 * return tokens;
 * }
 *
 * }
 */
