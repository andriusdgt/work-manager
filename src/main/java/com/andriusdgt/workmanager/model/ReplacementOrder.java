package com.andriusdgt.workmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

@Entity
@Table(name = "replacement_order")
@Getter
@Setter
@NoArgsConstructor
public class ReplacementOrder {

    @Id
    @GeneratedValue
    private Long id;

    private String department;

    @JsonProperty("start_date")
    private LocalDate startDate;

    @JsonProperty("end_date")
    private LocalDate endDate;

    @JsonProperty("factory_name")
    private String factoryName;

    @JsonProperty("factory_order_number")
    private String factoryOrderNumber;

    private Currency currency;

    private BigDecimal cost;

    @OneToMany
    private List<Part> parts;

}
