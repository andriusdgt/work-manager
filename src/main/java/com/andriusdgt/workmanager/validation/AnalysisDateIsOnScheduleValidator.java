package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.RepairOrder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AnalysisDateIsOnScheduleValidator implements ConstraintValidator<AnalysisDateIsOnSchedule, RepairOrder> {

    @Override
    public boolean isValid(RepairOrder ro, ConstraintValidatorContext constraintValidatorContext) {
        return ro.getAnalysisDate().isAfter(ro.getStartDate()) && ro.getAnalysisDate().isBefore(ro.getEndDate());
    }

}
