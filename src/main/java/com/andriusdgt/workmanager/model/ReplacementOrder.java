package com.andriusdgt.workmanager.model;

import com.andriusdgt.workmanager.validation.PartInventoryNumberNotEmpty;
import com.andriusdgt.workmanager.validation.StartEndDatesAreInOrder;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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
@StartEndDatesAreInOrder
public class ReplacementOrder implements Schedulable {

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
    @PartInventoryNumberNotEmpty
    private List<Part> parts;

}
