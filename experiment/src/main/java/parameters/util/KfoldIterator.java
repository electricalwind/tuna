package parameters.util;

import util.Assert;

import java.util.Collection;
import java.util.Iterator;

public class KfoldIterator<T> implements Iterator<FoldPartition<T>> {
    
    private final Partition<T> partition;
    private final int k;
    private int index;
    
    public KfoldIterator(int k, Collection<T> elements) {
        Assert.isTrue(k > 0);
        Assert.notNull(elements);
        
        this.k = k;
        this.partition = new Partition<>(elements, k);
        index = 0;
    }

    @Override
    public boolean hasNext() {
        return index < k;
    }

    @Override
    public FoldPartition<T> next() {
        FoldPartition<T> result = new FoldPartition<>(
                partition.getAllExcept(index), partition.get(index));
        
        index++;
        
        return result;
    }
    

    
}
