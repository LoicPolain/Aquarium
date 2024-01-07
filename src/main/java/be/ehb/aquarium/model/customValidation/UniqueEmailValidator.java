package be.ehb.aquarium.model.customValidation;

import be.ehb.aquarium.model.dao.customDAO.UserCustomDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Configurable;

import java.sql.SQLException;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


    /**
     * This method verifies if a given email exist in the DB. It uses the userCustomDAO. It checks for an unique email.
     * @param string : this is the eamil that needs to be verified.
     * @param constraintValidatorContext
     * @return True if the specified email is not present in the database.
     */
    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        UserCustomDAO userCustomDAO = new UserCustomDAO();
        try {
            return userCustomDAO.checkExistingEmail(string);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
