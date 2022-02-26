package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.ExtendedValidationGroup;
import com.andriusdgt.workmanager.validation.StartEndDatesAreInOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "analysis_order")
@Getter
@Setter
@NoArgsConstructor
@StartEndDatesAreInOrder(groups = ExtendedValidationGroup.class)
@GroupSequence({AnalysisOrder.class, ExtendedValidationGroup.class})
public class AnalysisOrder implements Schedulable {

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
    private Currency currency;

    @NotNull
    @Positive
    private BigDecimal cost;

    @NotNull
    @OneToMany
    private List<Part> parts;

}
