package defectstudy.callable;

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

import modelling.util.Pair;
import tokenizer.file.AbstractFileTokenizer;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.concurrent.Callable;

public class TokenizeF implements Callable<Pair<String, Iterable<String>>> {
    private final String pathToRemove;
    private String path;
    private AbstractFileTokenizer tokenizer;

    public TokenizeF(String path, AbstractFileTokenizer tokenizer, String pathToRemove) {
        this.path = path;
        this.tokenizer = tokenizer;
        this.pathToRemove = pathToRemove;
    }

    @Override
    public Pair<String, Iterable<String>> call() throws Exception {
        Reader reader = new FileReader(new File(path));
        return new Pair<>(path.replace(pathToRemove, ""), tokenizer.tokenize(reader));

    }


}
