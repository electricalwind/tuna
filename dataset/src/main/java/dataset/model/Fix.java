package dataset.model;

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
 * Fix
 * A class that gather information on a fix that occured on a specific file
 * it contains the information on the file before and after the fix as well as the commit corresponding to the old version of the file(lat time it was modified before)
 */
public class Fix implements Serializable{
    private static final long serialVersionUID = 20180618L;
    private final FileInterest fileBefore;
    private final FileInterest fileAfter;
    private final String oldHash;

    public Fix(FileInterest fileBefore, FileInterest fileAfter, String oldHash) {
        this.fileBefore = fileBefore;
        this.fileAfter = fileAfter;

        this.oldHash = oldHash;
    }

    public FileInterest getFileBefore() {
        return fileBefore;
    }

    public FileInterest getFileAfter() {
        return fileAfter;
    }

    public String getOldHash() {
        return oldHash;
    }
}
