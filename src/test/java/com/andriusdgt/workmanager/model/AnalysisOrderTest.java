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

class AnalysisOrderTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void doesNotFindValidationErrors() {
        AnalysisOrder analysisOrder = createAnalysisOrder();

        Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    public void validatesFieldNullability() {
        AnalysisOrder analysisOrder = AnalysisOrder.builder().build();

        Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

        assertThat(violations).hasSize(6);
    }

    @Test
    public void costCannotBeNegative() {
        AnalysisOrder analysisOrder = createAnalysisOrder().toBuilder()
                .cost(BigDecimal.valueOf(-2L))
                .build();

        Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void costCannotBeZero() {
        AnalysisOrder analysisOrder = createAnalysisOrder().toBuilder()
                .cost(BigDecimal.ZERO)
                .build();

        Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void acceptsOrderWithNoParts() {
        AnalysisOrder analysisOrder = createAnalysisOrder().toBuilder()
                .parts(List.of())
                .build();

        Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

        assertThat(violations).isEmpty();
    }

    @Nested
    @DisplayName("GOD annotation tests")
    class GodAnnotationTests {

        @Test
        public void startDateMustBeBeforeEndDate() {
            AnalysisOrder analysisOrder = createAnalysisOrder().toBuilder()
                    .startDate(LocalDate.of(2022, 2, 26))
                    .endDate(LocalDate.of(2022, 2, 24))
                    .build();

            Set<ConstraintViolation<AnalysisOrder>> violations = validator.validate(analysisOrder);

            assertThat(violations).hasSize(1);
        }

    }

    private AnalysisOrder createAnalysisOrder() {
        return AnalysisOrder.builder()
                .department(Department.ANALYSIS)
                .startDate(LocalDate.of(2021, 2, 25))
                .endDate(LocalDate.of(2021, 2, 26))
                .currency(Currency.getInstance("EUR"))
                .cost(BigDecimal.valueOf(112.11))
                .parts(List.of(Part.builder().count(1).inventoryNumber("foo").name("bar").build()))
                .build();
    }

}
