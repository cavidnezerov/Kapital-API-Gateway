package com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration;

public enum OperationType {
    CREATE_ORDER("CreateOrder"),
    GET_ORDER_STATUS("GetOrderStatus");

    private final String value;

    OperationType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
