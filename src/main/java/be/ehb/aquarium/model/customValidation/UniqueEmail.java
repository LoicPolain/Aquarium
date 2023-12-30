package be.ehb.aquarium.model.customValidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UniqueEmailValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UniqueEmail {
    String message() default "Email address does already exist!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
