package tokenizer.file.java.strategy;

import java.util.List;

public class BreadthFirstStrategy implements TreeVisitorStrategy {

    private static final String NAME = "Breadth-first search";
    
    @Override
    public void addChildrenToQueue(List queue, List children) {
        children.forEach(child -> queue.add(child));
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}
