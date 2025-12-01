package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static java.lang.invoke.MethodHandles.lookup;

public class LogUtil {

    Logger log;

    public LogUtil(){
        this.log = LoggerFactory.getLogger(lookup().lookupClass());
    }

    public Logger getLogger() {
        return log;
    }
}
