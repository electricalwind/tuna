package parameters.util;

import util.Assert;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Partition<T> implements Iterable {

    List<Collection<T>> partition = new LinkedList<>();
    
    public Partition(Collection<T> elements, int k) {
        int size = elements.size() / k;
        int i = 0;
        List<T> subset = new LinkedList<>();
        for (T element : elements) {
            if (i == size) {
                partition.add(subset);
                subset = new LinkedList<>();
                i = 0;
            }
            subset.add(element);
            i++;
        }
        partition.add(subset);
    }
    
    public Iterable<T> get(int i) {
        Assert.isTrue(i >= 0);
        Assert.isTrue(i < partition.size());
        
        return partition.get(i);
    }
    
    public Iterable<T> getAllExcept(int index) {
        List<T> result = new LinkedList<>();

        for (int i = 0; i < partition.size(); i++) {
            if (i != index) {
                result.addAll(partition.get(i));
            }
        }

        return result;
    }
    
    public int size() {
        return partition.size();
    }
    
    @Override
    public Iterator iterator() {
        return partition.iterator();
    }
}
