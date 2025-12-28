package AniketTestAcademy.resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

        private static  Properties properties;


    public ConfigReader() throws IOException {
        properties = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+ "/src/main/resources/GlobalData.properties");
        properties.load(fis);
    }


        public String getBrowser() {
            return properties.getProperty("browser");
        }

        public String getApplicationUrl() {
            return properties.getProperty("url");
        }

        public String getUserName() {
            return properties.getProperty("userName");
        }

        public String getPassword() {
            return properties.getProperty("password");
        }

}
