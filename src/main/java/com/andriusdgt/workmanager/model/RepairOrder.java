package com.andriusdgt.workmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "repair_order")
@Getter
@Setter
@NoArgsConstructor
public class RepairOrder {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Department department;

    @NotNull
    @JsonProperty("start_date")
    private LocalDate startDate;

    @NotNull
    @JsonProperty("end_date")
    private LocalDate endDate;

    @NotNull
    @JsonProperty("analysis_date")
    private LocalDate analysisDate;

    @NotNull
    @JsonProperty("test_date")
    private LocalDate testDate;

    @NotNull
    @JsonProperty("responsible_person")
    private String responsiblePerson;

    @NotNull
    private Currency currency;

    @NotNull
    @Positive
    private BigDecimal cost;

    @NotNull
    @Size(min = 1, message = "size must be greater than zero")
    @OneToMany
    private List<Part> parts;

}
