package dataset.callable;

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

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import dataset.model.Bug;

import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class implementing callable that use result from the bug tracker (accessed through rest services) to detemine whether a given issue is indeed a bug
 */
public class OnlineAnalysis implements Callable<Bug> {
    private final Bug bug;
    private static Pattern patternVersion = Pattern.compile(".*\"versions\":\\[(.*?)]");
    private static Pattern patternVname = Pattern.compile(".*?\"name\":\"([0-9.]+)\"");

    /**
     * Constructor
     * @param bug to consider
     */
    public OnlineAnalysis(Bug bug) {
        this.bug = bug;
    }

    /**
     * @return the bug received as an input if deemed valid, null otherwise
     */
    @Override
    public Bug call() throws UnirestException {
        String adress = "https://issues.apache.org/jira/rest/api/2/issue/" + bug.getIdentifier();
        String lookfor = "https://issues.apache.org/jira/rest/api/2/issuetype/1";
        String response = Unirest.get(adress).asJson().getBody().toString();

        if (response.contains(lookfor)) {
            Matcher matcher = patternVersion.matcher(response);
            if (matcher.find()) {
                String found = matcher.group(1);
                matcher = patternVname.matcher(found);
                while (matcher.find()) {
                    bug.getAffectedVersion().add(matcher.group(1));
                }
            }
            return bug;
        }
        return null;
    }
}
