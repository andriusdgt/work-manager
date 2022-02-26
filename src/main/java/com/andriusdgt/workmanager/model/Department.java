package com.andriusdgt.workmanager.model;

public enum Department {

    ANALYSIS("GOD analysis department"),
    REPAIR("GOD repair department"),
    REPLACEMENT("GOD replacement department");

    private final String formatted;

    Department(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return formatted;
    }

}
