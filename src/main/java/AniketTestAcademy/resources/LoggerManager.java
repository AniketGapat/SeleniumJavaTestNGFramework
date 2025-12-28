package AniketTestAcademy.resources;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class LoggerManager {

    //This method will return the logger object of provided class
    public static Logger getLogger(Class<?> cls) {
        // Implementation to return logger instance
        return LogManager.getLogger(); // Placeholder return statement
    }
}
