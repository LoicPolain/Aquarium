package be.ehb.aquarium.model.dao.customDAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppPropertiesBridge {
    public static final Properties properties;

    static {
        properties = new Properties();

        ClassLoader classLoader = AppPropertiesBridge.class.getClassLoader();
        InputStream applicationPropertiesStream = classLoader.getResourceAsStream("application.properties");
        try {
            properties.load(applicationPropertiesStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
