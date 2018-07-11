package parameters.setup;

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

import modelling.infrastructure.KylmSmootherFactory;
import modelling.infrastructure.kylm.ngram.smoother.NgramSmoother;

import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class SetupSet {
    private static final int[] SIZES = {2 };//3, 4, 5, 6, 7};
    private static final List<NgramSmoother> SMOOTHERS = smoothers();
    private static final int[] THRESHOLDS = {1}; //2, 4, 8, 16};

    private static final SetupSet INSTANCE = new SetupSet();

    private final List<Setup> setups
            = new LinkedList<>();


    private SetupSet() {
        for (NgramSmoother smoother : SMOOTHERS) {
            for (int size : SIZES) {
                for (int threshold : THRESHOLDS) {
                    setups.add(new Setup(
                            size, smoother, threshold));
                }
            }
        }

    }

    public static SetupSet instance() {
        return INSTANCE;
    }

    public Iterable<Setup> setups() {
        return setups;
    }

    private static List<NgramSmoother> smoothers() {
        List<NgramSmoother> result = new LinkedList<>();
        result.add(KylmSmootherFactory.absoluteDiscounting());
        result.add(KylmSmootherFactory.kneserNey());
        result.add(KylmSmootherFactory.modifiedKneserNey());
        result.add(KylmSmootherFactory.wittenBell());

        return result;
    }
}
