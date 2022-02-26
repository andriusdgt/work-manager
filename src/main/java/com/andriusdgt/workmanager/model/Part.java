package com.andriusdgt.workmanager.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Part {

    @Id
    @GeneratedValue
    @ApiModelProperty(hidden = true)
    private long id;

    private String inventoryNumber;

    private String name;

    private int count;

}
