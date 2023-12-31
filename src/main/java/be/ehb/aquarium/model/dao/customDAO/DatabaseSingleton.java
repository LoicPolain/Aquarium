package be.ehb.aquarium.model.dao.customDAO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Service
public class DatabaseSingleton {
    private static DatabaseSingleton databaseSingleton;
    private Connection connection;

    public static DatabaseSingleton getDatabaseSingleton(){
        if (databaseSingleton == null){
            databaseSingleton = new DatabaseSingleton();
        }
        return databaseSingleton;
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()){
            String DB_URL = AppPropertiesBridge.properties.get("spring.datasource.url").toString();
            String DB_USER = AppPropertiesBridge.properties.get("spring.datasource.username").toString();
            String DB_PASSWORD = AppPropertiesBridge.properties.get("spring.datasource.password").toString();
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        }
        return connection;
    }
}
