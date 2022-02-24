package com.andriusdgt.workmanager.model;

public enum Department {

    ANALYSIS("GOoD analysis department"),
    REPAIR("GOoD repair department"),
    REPLACEMENT("GOoD replacementdepartment");

    private final String formatted;

    Department(String formatted) {
        this.formatted = formatted;
    }

    @Override
    public String toString() {
        return formatted;
    }

}
