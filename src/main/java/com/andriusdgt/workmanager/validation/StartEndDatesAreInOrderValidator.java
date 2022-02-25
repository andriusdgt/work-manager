package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.Schedulable;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartEndDatesAreInOrderValidator implements ConstraintValidator<StartEndDatesAreInOrder, Schedulable> {

    @Override
    public boolean isValid(Schedulable s, ConstraintValidatorContext context) {
        return s.getStartDate().isBefore(s.getEndDate());
    }

}
