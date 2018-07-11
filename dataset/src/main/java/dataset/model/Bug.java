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
import java.util.List;

/**
 * Bug
 * A class representing a bug in a project
 * A bug has:
 *  An identifier,e.g., BCEL-65
 *  A message (extracted from the commit that patched it)
 *  A timestamp (time of the patch)
 *  A list of Fix Files
 *  A hash (of the commit)
 *  and A list od affected Version
 */
public class Bug implements Serializable{
    private static final long serialVersionUID = 20180618L;
    private final String identifier;
    private final String message;
    private final long timestamp;
    private final List<Fix> fixes;
    private final String hash;
    private List<String> affectedVersion;

    public Bug(String identifier, String hash,String message, long timestamp, List<String> affectedVersion, List<Fix> fixes) {
        this.identifier = identifier;
        this.hash = hash;
        this.message = message;
        this.timestamp = timestamp;
        this.fixes = fixes;
        this.affectedVersion = affectedVersion;
    }


    public String getIdentifier() {
        return identifier;
    }

    public String getMessage() {
        return message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public List<Fix> getFixes() {
        return fixes;
    }

    public String getHash() {
        return hash;
    }

    public List<String> getAffectedVersion() {
        return affectedVersion;
    }
}
