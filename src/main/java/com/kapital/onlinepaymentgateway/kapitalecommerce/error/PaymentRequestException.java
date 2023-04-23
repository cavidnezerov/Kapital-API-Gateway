package com.kapital.onlinepaymentgateway.kapitalecommerce.error;

public class PaymentRequestException extends RuntimeException{
    private String localMessage;

    public PaymentRequestException(String message, String localMessage) {
        super(message);
        this.localMessage = localMessage;
    }

    public String getLocalMessage() {
        return localMessage;
    }

    public void setLocalMessage(String localMessage) {
        this.localMessage = localMessage;
    }
}
