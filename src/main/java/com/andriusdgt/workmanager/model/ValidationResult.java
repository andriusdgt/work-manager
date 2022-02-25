package com.andriusdgt.workmanager.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "validation_result")
@Getter
@Setter
@Builder
public class ValidationResult {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    private String workOrderType;

    private String department;

    private boolean valid;

}
