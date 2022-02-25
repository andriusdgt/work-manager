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
@Constraint(validatedBy = AnalysisDateIsOnScheduleValidator.class)
public @interface AnalysisDateIsOnSchedule {

    String message() default "Analysis date must be between start and end date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
