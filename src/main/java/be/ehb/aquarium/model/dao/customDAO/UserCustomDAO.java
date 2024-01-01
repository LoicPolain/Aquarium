package be.ehb.aquarium.model.dao.customDAO;

import be.ehb.aquarium.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * This is a custom userDAO class.
 *
 * USAGE:
 * This is used inside the uniqueEmailValidation, in order to verify if an user with a given email exist in the DB.
 */
public class UserCustomDAO extends BaseDAO{
    /**
     * This method checks if an user is present in the DB with a given email.
     * @param email
     * @return if no user was found, the method returns true. Which means the email is unique.
     * @throws SQLException
     */
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
