package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.ExtendedValidationGroup;
import com.andriusdgt.workmanager.validation.PartInventoryNumberNotEmpty;
import com.andriusdgt.workmanager.validation.StartEndDatesAreInOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

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
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
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
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @NotEmpty
    private String factoryName;

    @NotNull
    @Pattern(regexp="^[a-zA-Z]{2}[0-9]{8}", message = "must consist of 2 letters followed by 8 digits")
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
