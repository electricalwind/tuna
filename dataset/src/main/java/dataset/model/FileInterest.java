package dataset.model;

import java.io.Serializable;

/**
 * FileInterest
 * A class that represent a source code file withe its path and content
 */
public class FileInterest implements Serializable{
    private static final long serialVersionUID = 20180618L;
    private final String fileContent;
    private final String filePath;


    public FileInterest(String fileContent, String filePath) {
        this.fileContent = fileContent;
        this.filePath = filePath;
    }

    public String getFileContent() {
        return fileContent;
    }

    public String getFilePath() {
        return filePath;
    }
}
