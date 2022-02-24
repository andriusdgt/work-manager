package com.andriusdgt.workmanager.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Part {

    @Id
    @GeneratedValue
    private long id;

    @JsonProperty("inventory_number")
    private String inventoryNumber;

    private String name;

    private int count;

}
