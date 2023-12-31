package be.ehb.aquarium.model.dao.customDAO;

import be.ehb.aquarium.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserCustomDAO extends BaseDAO{
    public boolean checkExistingEmail(String email) throws SQLException{
        try (PreparedStatement s = getConnection().prepareStatement("select * from user where email = ?")){
            s.setString(1, email);
            ResultSet rs = s.executeQuery();
            if (rs.next()){
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
