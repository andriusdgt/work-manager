package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.RepairOrder;
import com.andriusdgt.workmanager.model.RepairOrderTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class AnalysisDateIsOnScheduleValidatorTest {

    AnalysisDateIsOnScheduleValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new AnalysisDateIsOnScheduleValidator();
    }

    @Test
    public void isValid() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder();

        assertTrue(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustBeAfterStartDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 16))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void cannotEqualStartDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 20))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustBeBeforeEndDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 28))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void cannotEqualEndDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 26))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

}