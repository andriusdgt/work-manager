package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.ExtendedValidationGroup;
import com.andriusdgt.workmanager.validation.PartInventoryNumberNotEmpty;
import com.andriusdgt.workmanager.validation.StartEndDatesAreInOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "replacement_order")
@Getter
@Setter
@NoArgsConstructor
@StartEndDatesAreInOrder(groups = ExtendedValidationGroup.class)
@GroupSequence({ReplacementOrder.class, ExtendedValidationGroup.class})
public class ReplacementOrder implements Schedulable {

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
    @NotEmpty
    @JsonProperty("factory_name")
    private String factoryName;

    @NotNull
    @Pattern(regexp="^[a-zA-Z]{2}[0-9]{8}", message = "must consist of 2 letters followed by 8 digits")
    @JsonProperty("factory_order_number")
    private String factoryOrderNumber;

    @NotNull
    private Currency currency;

    @NotNull
    @Positive
    private BigDecimal cost;

    @NotNull
    @OneToMany
    @PartInventoryNumberNotEmpty(groups = ExtendedValidationGroup.class)
    private List<Part> parts;

}
