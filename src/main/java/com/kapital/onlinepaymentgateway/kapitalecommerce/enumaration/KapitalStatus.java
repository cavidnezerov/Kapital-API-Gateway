package com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration;

public enum KapitalStatus {
    SUCCESS("00"),
    NO_PERMISSION_FOR_CREATE_ORDER("10"),
    INVALID_REQUEST("30"),
    WRONG_OPERATION("54"),
    SYSTEM_ERROR("96");
    private final String value;

    KapitalStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
