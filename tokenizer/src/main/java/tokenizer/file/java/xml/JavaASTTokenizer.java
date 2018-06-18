package tokenizer.file.java.xml;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.*;
import com.github.javaparser.metamodel.NodeMetaModel;
import com.github.javaparser.metamodel.PropertyMetaModel;
import tokenizer.file.AbstractFileTokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.*;

import static com.github.javaparser.ParseStart.COMPILATION_UNIT;
import static com.github.javaparser.Providers.provider;
import static java.util.stream.Collectors.toList;

public class JavaASTTokenizer extends AbstractFileTokenizer {
    public final static String TYPE = "JavaASTTokenizer";

    @Override
    public Iterable<String>  tokenize(Reader reader) throws IOException {
        JavaParser jp = new JavaParser(JavaParser.getStaticConfiguration());
        List<String> tokens = new LinkedList<>();
        ParseResult<CompilationUnit> result = jp.parse(COMPILATION_UNIT, provider(reader));
        CompilationUnit compilationUnit = result.getResult().get();
        if (compilationUnit != null) {
            tokens.addAll(printNode(compilationUnit));
        }
        return tokens;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    public static List<String> printNode(Node node) {
        return printNode(node, "root");
    }

    public static List<String> printNode(Node node, String name) {
        List<String> tokens = new ArrayList<>();


        NodeMetaModel metaModel = node.getMetaModel();
        List<PropertyMetaModel> allPropertyMetaModels = metaModel.getAllPropertyMetaModels();
        List<PropertyMetaModel> attributes = allPropertyMetaModels.stream().filter(PropertyMetaModel::isAttribute).filter(PropertyMetaModel::isSingular).collect(toList());
        List<PropertyMetaModel> subNodes = allPropertyMetaModels.stream().filter(PropertyMetaModel::isNode).filter(PropertyMetaModel::isSingular).collect(toList());
        List<PropertyMetaModel> subLists = allPropertyMetaModels.stream().filter(PropertyMetaModel::isNodeList).collect(toList());

        if (name.equals("comment")) {
            return tokens;
        }
        tokens.add("<" + name + ">");
        //tokens.add(metaModel.getTypeName());


        for (PropertyMetaModel attributeMetaModel : attributes) {
            if (!attributeMetaModel.getValue(node).equals(false)) {
                tokens.add(attributeMetaModel.getName());
                tokens.add(attributeMetaModel.getValue(node).toString());
            }
        }


        for (PropertyMetaModel subNodeMetaModel : subNodes) {
            Node value = (Node) subNodeMetaModel.getValue(node);
            if (value != null) {
                tokens.addAll(printNode(value, subNodeMetaModel.getName()));
            }
        }

        for (PropertyMetaModel subListMetaModel : subLists) {
            NodeList<? extends Node> subList = (NodeList<? extends Node>) subListMetaModel.getValue(node);
            if (subList != null && !subList.isEmpty()) {
                String listName = subListMetaModel.getName();
                tokens.add("<" + listName + ">");
                String singular = listName.substring(0, listName.length() - 1);
                for (Node subListNode : subList) {
                    tokens.addAll(printNode(subListNode, singular));
                }
                tokens.add("</" + listName + ">");
            }
        }
        tokens.add("</" + name + ">");
        return tokens;
    }

}
