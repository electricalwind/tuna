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
import java.util.Map;

/**
 * Software
 * A class gathering the required intel on a software project to create the dataset
 * it contains:
 *  the name of the software
 *  the abreviation used for issues
 *  the url of the git remote repository
 *  and A list of all versions of the software and their corresponding commit
 */
public class Software implements Serializable {
    private static final long serialVersionUID = 20180618L;
    private final String name;
    private final String abreviation;
    private final String gitUrl;
    private final Map<String, String> versions;

    public Software(String name, String abreviation, String gitUrl, Map<String, String> versions) {
        this.name = name;
        this.abreviation = abreviation;
        this.gitUrl = gitUrl;
        this.versions = versions;
    }

    public Map<String, String> getVersions() {
        return versions;
    }

    public String getGitUrl() {
        return gitUrl;
    }

    public String getAbreviation() {
        return abreviation;
    }

    public String getName() {
        return name;
    }
}
