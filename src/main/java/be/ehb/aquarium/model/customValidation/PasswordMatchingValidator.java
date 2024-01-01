package be.ehb.aquarium.model.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;


/**
 * This code has been copied from the internet: https://www.bezkoder.com/spring-boot-custom-validation/
 */
public class PasswordMatchingValidator implements ConstraintValidator<MatchingPassword, Object> {
    private String password;
    private String confirmPassword;
    @Override
    public void initialize(MatchingPassword matchingPassword) {
        this.password = matchingPassword.password();
        this.confirmPassword = matchingPassword.confirmPassword();
    }

    /**
     * This method verifies whether the provided password fields match.
     * @param o
     * @param constraintValidatorContext
     * @return true if both passwords match, else false
     */
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object passwordValue = new BeanWrapperImpl(o).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(o).getPropertyValue(confirmPassword);
        return (passwordValue != null) ? passwordValue.equals(confirmPasswordValue) : confirmPasswordValue == null;
    }
}
