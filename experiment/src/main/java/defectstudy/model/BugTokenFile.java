package defectstudy.model;

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
