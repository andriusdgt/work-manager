package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.AnalysisDateIsOnSchedule;
import com.andriusdgt.workmanager.validation.ExtendedValidationGroup;
import com.andriusdgt.workmanager.validation.StartEndDatesAreInOrder;
import com.andriusdgt.workmanager.validation.TestDateIsOnSchedule;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    private LocalDate analysisDate;

    @NotNull
    private LocalDate testDate;

    @NotNull
    @NotEmpty
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
