package com.andriusdgt.workmanager.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = PartInventoryNumberNotEmptyValidator.class)
public @interface PartInventoryNumberNotEmpty {

    String message() default "part inventory number must not be empty";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
