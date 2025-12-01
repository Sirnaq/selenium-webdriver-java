package utils;

import org.slf4j.Logger;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtil {

    Path tempFile;
    String absolutePath;
    Logger log;

    public FileUtil(String prefix, String suffix) {
        try{
            tempFile = Files.createTempFile(prefix, suffix);
        } catch (Exception ignore){}
        log = new LogUtil().getLogger();
    }

    public String getAbsolutePath(){
        this.absolutePath = tempFile.toAbsolutePath().toString();
        return absolutePath;
    }

    public void logFileName(){
        log.debug("Using temporal file {} in file uploading", absolutePath);
    }

}
