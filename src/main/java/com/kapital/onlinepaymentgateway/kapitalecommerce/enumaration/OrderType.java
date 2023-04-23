package com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration;

public enum OrderType {
    PURCHASE("Purchase");

    private final String value;

    OrderType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}
