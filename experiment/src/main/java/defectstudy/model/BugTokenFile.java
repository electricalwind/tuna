package defectstudy.model;

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

import java.io.Serializable;

/**
 *
 */
public class BugTokenFile implements Serializable {
    private static final long serialVersionUID = 20180618L;
    private String file;
    private String budID;
    private String tokenizerAbv;

    public BugTokenFile(String file1, String budID1, String tokenizerAbv1) {
        this.file = file1;
        this.budID = budID1;
        this.tokenizerAbv = tokenizerAbv1;
    }

    public String getFile() {
        return file;
    }

    public String getBudID() {
        return budID;
    }

    public String getTokenizerAbv() {
        return tokenizerAbv;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BugTokenFile)) return false;

        BugTokenFile that = (BugTokenFile) o;

        if (budID != that.budID) return false;
        if (!file.equals(that.file)) return false;
        return tokenizerAbv.equals(that.tokenizerAbv);
    }

    @Override
    public int hashCode() {
        int result = file.hashCode();
        result = 31 * result + budID.hashCode();
        result = 31 * result + tokenizerAbv.hashCode();
        return result;
    }
}
