package com.andriusdgt.workmanager.controller;

import com.andriusdgt.workmanager.model.ValidationResult;
import com.andriusdgt.workmanager.repository.ValidationResultRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionAdvice {

    private final ValidationResultRepository validationResultRepository;

    public ControllerExceptionAdvice(ValidationResultRepository validationResultRepository) {
        this.validationResultRepository = validationResultRepository;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> invalidFormatException(final HttpMessageNotReadableException e) {
        return new ResponseEntity(new ErrorResponse(e.getCause().toString()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            ValidationResult validationResult = ValidationResult.builder()
                    .date(LocalDate.now())
                    .workOrderType(error.getObjectName())
                    .valid(false)
                    .build();
            validationResultRepository.save(validationResult);

            String subjectName = error.getObjectName();
            if (error instanceof FieldError)
                subjectName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(subjectName, errorMessage);
        });
        return errors;
    }

}
