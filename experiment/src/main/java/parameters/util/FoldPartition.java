package parameters.util;

import modelling.util.assertion.Assert;

public class FoldPartition<T> {

    private final Iterable<T> trainingSet;
    private final Iterable<T> testSet;
    
    public FoldPartition(Iterable<T> trainingSet, Iterable<T> testSet) {
        Assert.notNull(trainingSet);
        Assert.notNull(testSet);
        
        this.trainingSet = trainingSet;
        this.testSet = testSet;
    }
    
    public Iterable<T> trainingSet() {
        return trainingSet;
    }
    
    public Iterable<T> testSet() {
        return testSet;
    }
    
}
