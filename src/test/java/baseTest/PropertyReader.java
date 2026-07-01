package baseTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {

    public static Properties prop;
    public static Properties expectations;
    public static PropertyReader instance;

    private PropertyReader() throws IOException {
        prop = getPropertyFile("./src/test/resources/Configuration/config.properties");
        expectations = getPropertyFile("./src/test/resources/Configuration/expectations.properties");
    }
    public static PropertyReader getInstance() throws IOException {
        if(instance == null) {
            instance = new PropertyReader();
        }
        return instance;
    }

    private Properties getPropertyFile(String configPath) throws IOException {
        Properties properties = new Properties();

        try {
            InputStream ip = new FileInputStream(configPath);
            properties.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public String readProperty(String key) {
        return prop.getProperty(key);
    }
    public String readExpectation(String key) {
        return expectations.getProperty(key);
    }
}
