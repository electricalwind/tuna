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
