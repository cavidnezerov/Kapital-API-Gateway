package com.kapital.onlinepaymentgateway.kapitalecommerce.controller;


import com.kapital.onlinepaymentgateway.kapitalecommerce.error.PaymentException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.PaymentRequestException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ResponseMessage;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.DeterminateResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.PaymentCreateRequest;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.PaymentCreateResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.TransactionDto;
import com.kapital.onlinepaymentgateway.kapitalecommerce.service.PaymentService;
import com.kapital.onlinepaymentgateway.kapitalecommerce.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final TransactionService transactionService;

    @PostMapping("/create-payment")
    public ResponseEntity<PaymentCreateResponse> createPayment(@RequestBody PaymentCreateRequest request) {
        return ResponseEntity.ok(paymentService.createPayment(request));
    }

    @PostMapping("/determinate-payment")
    @SneakyThrows
    public DeterminateResponse determinatePayment(@RequestParam("TRANSACTIONID") Long id,
                                                  @RequestParam("STATUS") String status) {
        var response = paymentService.determinatePayment(id, status);
        response.setPaymentStatus(false);
        if (response.isRequestStatus()) {
            if (response.isPaymentStatus()) {
                return response;
            } else {
                throw new PaymentException(ResponseMessage.KAPITAL_PAYMENT_FAILED);
            }
        } else {
            throw new PaymentRequestException(ResponseMessage.KAPITAL_REQUEST_FAILED, "transactionId: " + id);
        }
    }

    @GetMapping("/transactions")
    public List<TransactionDto> get() {
        return transactionService.get();
    }

}