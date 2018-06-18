package gitutils;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utils class to get the list of files with a given extension recursively in a directory
 */
public class FilesOfInterest {
    /**
     * Get the recursively the list of file with a given extension in a given folder
     * @param folder to investigate
     * @param extension to use
     * @return the list of path
     * @throws IOException
     */
    public static List<String> list(String folder, String extension) throws IOException {
        Pattern pattern = Pattern.compile(".*\\."+extension);
        return Files.find(Paths.get(folder), 999, (p, bfa) -> bfa.isRegularFile()  && pattern.matcher(p.getFileName().toString()).matches()).map(Path::toString).collect(Collectors.toList());
    }

    /**
     * Get the recursively the list of file with a given extension in a given folder with a given subpath
     * @param folder to investigate
     * @param extension to use
     * @param subpath required
     * @return the list of path
     * @throws IOException
     */
    public static List<String> list(String folder, String extension, String subpath) throws IOException {
        List<String> result = new ArrayList<>();
        
        List<String> candidates = list(folder, extension);
        
        subpath = subpath.replace("/", "\\/");
        String regex = ".*" + subpath + ".*\\." + extension;
        Pattern pattern = Pattern.compile(regex);
        for (String candidate : candidates) {
            if (pattern.matcher(candidate).matches()) {
                result.add(candidate);
            }
        }
        
        return result;
    }
}
