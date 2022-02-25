package com.andriusdgt.workmanager.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;

@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = StartEndDatesAreInOrderValidator.class)
public @interface StartEndDatesAreInOrder {

    String message() default "start date must be before end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
