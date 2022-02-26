package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.Schedulable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class StartEndDatesAreInOrderValidatorTest {

    StartEndDatesAreInOrderValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new StartEndDatesAreInOrderValidator();
    }

    @Test
    public void isValid() {
        Schedulable someOrder = new SomeOrder(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 2, 21));

        assertTrue(validator.isValid(someOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void datesCannotBeEqual() {
        Schedulable someOrder = new SomeOrder(LocalDate.of(2022, 2, 21), LocalDate.of(2022, 2, 21));

        assertFalse(validator.isValid(someOrder, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void startDateMustBeBeforeEndDate() {
        Schedulable someOrder = new SomeOrder(LocalDate.of(2022, 2, 21), LocalDate.of(2022, 2, 1));

        assertFalse(validator.isValid(someOrder, mock(ConstraintValidatorContext.class)));
    }

    private static class SomeOrder implements Schedulable {

        private final LocalDate startDate;
        private final LocalDate endDate;

        public SomeOrder(LocalDate startDate, LocalDate endDate) {
            this.startDate = startDate;
            this.endDate = endDate;
        }

        @Override
        public LocalDate getStartDate() {
            return startDate;
        }

        @Override
        public LocalDate getEndDate() {
            return endDate;
        }
    }

}