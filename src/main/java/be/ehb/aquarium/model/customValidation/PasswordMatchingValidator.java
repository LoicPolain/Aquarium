package be.ehb.aquarium.model.customValidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordMatchingValidator implements ConstraintValidator<MatchingPassword, Object> {
    private String password;
    private String confirmPassword;
    @Override
    public void initialize(MatchingPassword matchingPassword) {
        this.password = matchingPassword.password();
        this.confirmPassword = matchingPassword.confirmPassword();
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        Object passwordValue = new BeanWrapperImpl(o).getPropertyValue(password);
        Object confirmPasswordValue = new BeanWrapperImpl(o).getPropertyValue(confirmPassword);
        System.out.println(passwordValue);
        System.out.println(confirmPasswordValue);
        return (passwordValue != null) ? passwordValue.equals(confirmPasswordValue) : confirmPasswordValue == null;
    }
}
