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
public class BugResult implements Serializable{
    private static final long serialVersionUID = 20180618L;
    private String berelease;
    private String buggy;
    private String fixed;
    private String afrelease;
    private String releaseAffected;

    public BugResult() {
    }

    public String getBerelease() {
        return berelease;
    }

    public void setBerelease(String berelease) {
        this.berelease = berelease;
    }

    public String getBuggy() {
        return buggy;
    }

    public void setBuggy(String buggy) {
        this.buggy = buggy;
    }

    public String getFixed() {
        return fixed;
    }

    public void setFixed(String fixed) {
        this.fixed = fixed;
    }

    public String getAfrelease() {
        return afrelease;
    }

    public void setAfrelease(String afrelease) {
        this.afrelease = afrelease;
    }

    public String getReleaseAffected() {
        return releaseAffected;
    }

    public void setReleaseAffected(String releaseAffected) {
        this.releaseAffected = releaseAffected;
    }
}
