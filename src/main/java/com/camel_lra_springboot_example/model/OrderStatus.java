package com.camel_lra_springboot_example.model;

public enum OrderStatus {

    INITIATED("Initiated"), COMPLETED("Completed"), FAILED("Failed");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
