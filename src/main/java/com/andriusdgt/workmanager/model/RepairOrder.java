package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
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
@StartEndDatesAreInOrder(groups = ExtendedValidationGroup.class)
@AnalysisDateIsOnSchedule(groups = ExtendedValidationGroup.class)
@TestDateIsOnSchedule(groups = ExtendedValidationGroup.class)
@GroupSequence({RepairOrder.class, ExtendedValidationGroup.class})
public class RepairOrder implements Schedulable {

    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "must be a valid department")
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
    @NotEmpty
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
