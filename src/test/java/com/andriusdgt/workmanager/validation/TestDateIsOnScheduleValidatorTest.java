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

class TestDateIsOnScheduleValidatorTest {

    TestDateIsOnScheduleValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new TestDateIsOnScheduleValidator();
    }

    @Test
    public void isValid() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 24))
                .build();

        assertTrue(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustBeAfterAnalysisDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 21))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustNotEqualAnalysisDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 22))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustBeBeforeEndDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 27))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void mustNotEqualEndDate() {
        RepairOrder repairOrder = RepairOrderTest.createRepairOrder().toBuilder()
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 26))
                .build();

        assertFalse(validator.isValid(repairOrder, mock(ConstraintValidatorContext.class)));
    }

}