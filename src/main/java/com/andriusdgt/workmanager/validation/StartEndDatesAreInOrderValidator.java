package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.Schedulable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartEndDatesAreInOrderValidator implements ConstraintValidator<StartEndDatesAreInOrder, Schedulable> {

    @Override
    public boolean isValid(Schedulable o, ConstraintValidatorContext context) {
        return o.getStartDate().isBefore(o.getEndDate());
    }

}
