package be.ehb.aquarium.model.customValidation;

import be.ehb.aquarium.model.dao.UserRepo;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.annotation.Annotation;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    private UserRepo userRepo;

    @Autowired
    public UniqueEmailValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return userRepo.findFirstByEmail(string) == null;
    }
}
