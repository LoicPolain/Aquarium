package be.ehb.aquarium.model.dao.customDAO;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseDAO {
    private Connection connection;

    public Connection getConnection()throws SQLException {
        if (connection == null || connection.isClosed()){
            connection = DatabaseSingleton.getDatabaseSingleton().getConnection();
        }
        return connection;
    }
}
