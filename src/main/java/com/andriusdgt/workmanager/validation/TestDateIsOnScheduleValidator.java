package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.RepairOrder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class TestDateIsOnScheduleValidator implements ConstraintValidator<TestDateIsOnSchedule, RepairOrder> {

    @Override
    public boolean isValid(RepairOrder ro, ConstraintValidatorContext context) {
        return ro.getTestDate().isAfter(ro.getAnalysisDate()) && ro.getTestDate().isBefore(ro.getEndDate());
    }

}
