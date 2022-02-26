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

class ReplacementOrderTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void doesNotFindValidationErrors() {
        ReplacementOrder replacementOrder = createReplacementOrder();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).isEmpty();
    }

    @Test
    public void validatesFieldNullability() {
        ReplacementOrder replacementOrder = ReplacementOrder.builder().build();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).hasSize(9);
    }

    @Test
    public void factoryOrderNumberMustMatchRegex() {
        ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                .factoryOrderNumber("12345678DE")
                .build();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void costCannotBeNegative() {
        ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                .cost(BigDecimal.valueOf(-2L))
                .build();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void costCannotBeZero() {
        ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                .cost(BigDecimal.ZERO)
                .build();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).hasSize(1);
    }

    @Test
    public void acceptsOrderWithNoParts() {
        ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                .parts(List.of())
                .build();

        Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

        assertThat(violations).isEmpty();
    }

    @Nested
    @DisplayName("GOD annotation tests")
    class GodAnnotationTests {

        @Test
        public void startDateMustBeBeforeEndDate() {
            ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                    .startDate(LocalDate.of(2022, 2, 26))
                    .endDate(LocalDate.of(2022, 2, 20))
                    .build();

            Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

            assertThat(violations.stream().map(ConstraintViolation::getMessage))
                    .contains("start date must be before end date");
        }

        @Test
        public void ifOrderContainsPartsThenInventoryNumbersAreNotEmpty() {
            ReplacementOrder replacementOrder = createReplacementOrder().toBuilder()
                    .parts(List.of(Part.builder().count(1).inventoryNumber("").name("bar").build()))
                    .build();

            Set<ConstraintViolation<ReplacementOrder>> violations = validator.validate(replacementOrder);

            assertThat(violations).hasSize(1);
        }

    }

    private ReplacementOrder createReplacementOrder() {
        return ReplacementOrder.builder()
                .department(Department.REPLACEMENT)
                .startDate(LocalDate.of(2022, 2, 20))
                .endDate(LocalDate.of(2022, 2, 24))
                .factoryName("GODfactory")
                .factoryOrderNumber("DE12345678")
                .currency(Currency.getInstance("EUR"))
                .cost(BigDecimal.valueOf(112.11))
                .parts(List.of(Part.builder().count(1).inventoryNumber("foo").name("bar").build()))
                .build();
    }
    
}