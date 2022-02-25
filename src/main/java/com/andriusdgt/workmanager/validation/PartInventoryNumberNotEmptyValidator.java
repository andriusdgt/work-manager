package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.Part;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class PartInventoryNumberNotEmptyValidator implements ConstraintValidator<PartInventoryNumberNotEmpty, List<Part>> {

    @Override
    public boolean isValid(List<Part> parts, ConstraintValidatorContext constraintValidatorContext) {
        return parts.stream().noneMatch(p -> p.getInventoryNumber() == null || p.getInventoryNumber().isEmpty());
    }

}
