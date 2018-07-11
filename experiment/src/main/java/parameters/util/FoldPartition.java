package parameters.util;

/*-
 * #%L
 * tuna
 * %%
 * Copyright (C) 2018 Maxime Cordy, Matthieu Jimenez, University of Luxembourg and Namur
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

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
