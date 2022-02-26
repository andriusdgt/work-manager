package com.andriusdgt.workmanager.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class RepairOrderTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void doesNotFindValidationErrors() {
        RepairOrder repairOrder = createRepairOrder();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    public void validatesFieldNullability() {
        RepairOrder repairOrder = RepairOrder.builder().build();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).hasSize(10);
    }

    @Test
    public void responsiblePersonShouldNotBeEmpty() {
        RepairOrder repairOrder = createRepairOrder().toBuilder()
                .responsiblePerson("")
                .build();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void responsiblePersonCanBeBlank() {
        RepairOrder repairOrder = createRepairOrder().toBuilder()
                .responsiblePerson("  ")
                .build();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    public void costCannotBeNegative() {
        RepairOrder repairOrder = createRepairOrder().toBuilder()
                .cost(BigDecimal.valueOf(-2L))
                .build();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void costCannotBeZero() {
        RepairOrder repairOrder = createRepairOrder().toBuilder()
                .cost(BigDecimal.ZERO)
                .build();

        Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

        assertThat(violations).hasSize(1);
    }

    @Nested
    @DisplayName("GOD annotation tests")
    class GodAnnotationTests {

        @Test
        public void startDateMustBeBeforeEndDate() {
            RepairOrder repairOrder = createRepairOrder().toBuilder()
                    .startDate(LocalDate.of(2022, 2, 26))
                    .endDate(LocalDate.of(2022, 2, 20))
                    .build();

            Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

            assertThat(violations.stream().map(ConstraintViolation::getMessage))
                    .contains("start date must be before end date");
        }

        @Test
        public void analysisDateMustBeBetweenStartAndEndDates() {
            RepairOrder repairOrder = createRepairOrder().toBuilder()
                    .startDate(LocalDate.of(2022, 2, 20))
                    .endDate(LocalDate.of(2022, 2, 26))
                    .analysisDate(LocalDate.of(2022, 2, 17))
                    .build();

            Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

            assertThat(violations.stream().map(ConstraintViolation::getMessage))
                    .contains("analysis date must be between start and end dates");
        }

        @Test
        public void testDateMustBeBetweenAnalysisAndEndDates() {
            RepairOrder repairOrder = createRepairOrder().toBuilder()
                    .startDate(LocalDate.of(2022, 2, 26))
                    .endDate(LocalDate.of(2022, 2, 20))
                    .build();

            Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

            assertThat(violations.stream().map(ConstraintViolation::getMessage))
                    .contains("test date must be between analysis and end dates");
        }

        @Test
        public void orderShouldContainAtLeastOnePart() {
            RepairOrder repairOrder = createRepairOrder().toBuilder()
                    .parts(List.of())
                    .build();

            Set<ConstraintViolation<RepairOrder>> violations = validator.validate(repairOrder);

            assertThat(violations).hasSize(1);
        }

    }

    public static RepairOrder createRepairOrder() {
        return RepairOrder.builder()
                .department(Department.REPAIR)
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 26))
                .analysisDate(LocalDate.of(2022, 2, 22))
                .testDate(LocalDate.of(2022, 2, 24))
                .responsiblePerson("John Smith")
                .currency(Currency.getInstance("EUR"))
                .cost(BigDecimal.valueOf(112.11))
                .parts(List.of(Part.builder().count(1).inventoryNumber("foo").name("bar").build()))
                .build();
    }

}