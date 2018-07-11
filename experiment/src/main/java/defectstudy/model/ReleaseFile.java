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
public class ReleaseFile implements Serializable {
    private static final long serialVersionUID = 20180618L;
    private final String file;
    private final int bugs;
    private final int fixed;
    private final int loc;


    public ReleaseFile(String file, int bugs, int fixed, int loc) {
        this.file = file;
        this.bugs = bugs;
        this.fixed = fixed;
        this.loc = loc;
    }

    public String getFile() {
        return file;
    }

    public int getBugs() {
        return bugs;
    }

    public int getFixed() {
        return fixed;
    }

    public int getLoc() {
        return loc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReleaseFile)) return false;

        ReleaseFile that = (ReleaseFile) o;

        return file.equals(that.file);
    }

    @Override
    public int hashCode() {
        return file.hashCode();
    }
}
