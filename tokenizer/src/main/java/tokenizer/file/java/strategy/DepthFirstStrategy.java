package tokenizer.file.java.strategy;

import java.util.List;

public class DepthFirstStrategy implements TreeVisitorStrategy {

    private static final String NAME = "Depth-first search";
    
    @Override
    public void addChildrenToQueue(List queue, List children) {
        for (int i = children.size(); i > 0; i--) {
            queue.add(0, children.get(i - 1));
        }
    }
    
    @Override
    public String toString() {
        return NAME;
    }
}
