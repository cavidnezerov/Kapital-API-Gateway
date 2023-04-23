package com.kapital.onlinepaymentgateway.kapitalecommerce.error;

public class CustomSslException extends RuntimeException{
    private String localMessage;

    public CustomSslException(String message, Throwable cause, String localMessage) {
        super(message, cause);
        this.localMessage = localMessage;
    }

    public String getLocalMessage() {
        return localMessage;
    }

    public void setLocalMessage(String localMessage) {
        this.localMessage = localMessage;
    }
}
