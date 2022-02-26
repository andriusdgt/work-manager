package com.andriusdgt.workmanager.validation;

import com.andriusdgt.workmanager.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintValidatorContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class PartInventoryNumberNotEmptyValidatorTest {

    PartInventoryNumberNotEmptyValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new PartInventoryNumberNotEmptyValidator();
    }

    @Test
    public void isValid() {
        List<Part> parts = List.of(
                Part.builder().count(1).inventoryNumber("foo").name("bar").build(),
                Part.builder().count(2).inventoryNumber("baz").name("foobar").build()
        );

        assertTrue(validator.isValid(parts, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void isValidOnEmptyList() {
        List<Part> parts = List.of();

        assertTrue(validator.isValid(parts, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void allPartsMustContainInventoryNumber() {
        List<Part> parts = List.of(
                Part.builder().count(1).inventoryNumber("foo").name("bar").build(),
                Part.builder().count(2).inventoryNumber("").name("foobar").build()
        );

        assertFalse(validator.isValid(parts, mock(ConstraintValidatorContext.class)));
    }

    @Test
    public void allPartInventoryNumbersMustNotBeNull() {
        List<Part> parts = List.of(
                Part.builder().count(1).inventoryNumber("foo").name("bar").build(),
                Part.builder().count(2).inventoryNumber(null).name("foobar").build()
        );

        assertFalse(validator.isValid(parts, mock(ConstraintValidatorContext.class)));
    }

}