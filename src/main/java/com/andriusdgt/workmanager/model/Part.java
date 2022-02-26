package com.andriusdgt.workmanager.model;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    private long id;

    private String inventoryNumber;

    private String name;

    private int count;

}
