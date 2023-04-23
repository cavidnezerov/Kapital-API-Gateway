package com.kapital.onlinepaymentgateway.kapitalecommerce.error.handler;

import com.kapital.onlinepaymentgateway.kapitalecommerce.error.CustomSslException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.PaymentException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.PaymentRequestException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ErrorResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> handlePaymentException(PaymentException ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTitle(HttpStatus.BAD_REQUEST.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PaymentRequestException.class)
    public ResponseEntity<ErrorResponse> handlePaymentRequestException(PaymentRequestException ex) {
        logger.error(ex.getMessage() + ex.getLocalMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTitle(HttpStatus.EXPECTATION_FAILED.getReasonPhrase());
        error.setMessage(ex.getMessage());

        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(CustomSslException.class)
    public ResponseEntity<ErrorResponse> handleCustomSslException(CustomSslException ex) {
        logger.error(ex.getMessage() + ex.getLocalMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setMessage(error.getMessage());

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnknown(Exception ex) {
        logger.error(ex.getMessage(), ex);

        ErrorResponse error = new ErrorResponse();
        error.setTitle(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
        error.setMessage(ResponseMessage.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
