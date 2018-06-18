package tokenizer.file.java.strategy;

import java.util.List;

public interface TreeVisitorStrategy<T> {
    
    public void addChildrenToQueue(List<T> queue, List<T> children);
    
}
