package be.ehb.aquarium.model.dao.customDAO;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * This class is used as a bridge to access the application.properties.
 *
 * USAGE:
 * It is used to get the credentials of the DB for the DatabaseSingleton class (custom DAO).
 *
 * This code has been copied from the internet: https://stackoverflow.com/questions/67106772/java-spring-boot-how-to-access-application-properties-values-in-normal-class
 */
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
