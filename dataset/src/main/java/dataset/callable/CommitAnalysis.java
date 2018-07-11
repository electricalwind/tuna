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

import dataset.model.Bug;
import org.eclipse.jgit.revwalk.RevCommit;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class implementing callable that csvExporter the commit message to find mention to the bug issue
 */
public class CommitAnalysis implements Callable<Bug> {
    private final Pattern regexp;
    private final RevCommit commit;

    /**
     * Constructor
     * @param next commit to csvExporter
     * @param abreviation software abreviation on the bug tracker
     */
    public CommitAnalysis(RevCommit next, String abreviation) {
        this.commit = next;
        this.regexp = Pattern.compile(".*(" + abreviation + "-[0-9]+)");
    }

    /**
     *
     * @return a bug object if the commit match null otherwise
     * */
    @Override
    public Bug call(){
        String fm = commit.getFullMessage();
        Matcher matcher = regexp.matcher(fm);
        if (matcher.find() && !fm.contains("Merge") && !fm.contains("Revert")) {
            String idBug = matcher.group(1);
            return new Bug(idBug, commit.getName(), commit.getFullMessage(), commit.getCommitTime(),new ArrayList<>() ,new ArrayList<>());
        }
        return null;
    }
}
