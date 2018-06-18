package defectstudy.model;

import java.io.Serializable;

/**
 *
 */
public class ReleaseFile implements Serializable {
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
