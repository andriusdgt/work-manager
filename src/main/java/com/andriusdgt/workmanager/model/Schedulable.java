package com.andriusdgt.workmanager.model;

import java.time.LocalDate;

public interface Schedulable {

    LocalDate getStartDate();

    LocalDate getEndDate();

}
