package be.ehb.aquarium.model.customValidation;

import be.ehb.aquarium.model.dao.UserRepo;
import be.ehb.aquarium.model.dao.customDAO.UserCustomDAO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@Configurable(autowire = Autowire.BY_TYPE, dependencyCheck = true)
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {


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
