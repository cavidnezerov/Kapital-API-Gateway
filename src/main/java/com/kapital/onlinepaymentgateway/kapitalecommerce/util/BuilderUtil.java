package com.kapital.onlinepaymentgateway.kapitalecommerce.util;

import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.Language;
import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.OrderType;
import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.TransactionStatus;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.PaymentCreateRequest;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.TransactionDto;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.kapital.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.OperationType.CREATE_ORDER;
import static com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.OperationType.GET_ORDER_STATUS;

@Component
public class BuilderUtil {
    @Value("${kapital.approve.url}")
    private String kapitalApproveUrl;
    @Value("${kapital.cancel.url}")
    private String kapitalCancelUrl;
    @Value("${kapital.decline.url}")
    private String kapitalDeclineUrl;
    @Value("${kapital.merchant.id}")
    private String kapitalMerchantId;
    public TransactionDto buildPendingTransaction(PaymentCreateRequest request) {
        return TransactionDto.builder()
                .description(request.getDescription())
                .amount(request.getAmount())
                .currencyNumber(request.getCurrencyNumber())
                .status(TransactionStatus.PENDING)
                .createdOn(LocalDateTime.now())
                .build();
    }

    public TKKPG_Request<PurchaseRequest> buildTkkpgPurchaseRequest(TransactionDto transaction) {
        var parameter = "&TRANSACTIONID=" + transaction.getId();

        return new TKKPG_Request<>(
                PurchaseRequest.builder()
                        .language(Language.AZ.name())
                        .operation(CREATE_ORDER.getValue())
                        .order(PurchaseRequestOrder.builder()
                                .orderType(OrderType.PURCHASE.getValue())
                                .amount(transaction.getAmount().multiply(BigDecimal.valueOf(100)))
                                .approveURL(kapitalApproveUrl + parameter)
                                .cancelURL(kapitalCancelUrl + parameter)
                                .declineURL(kapitalDeclineUrl + parameter)
                                .description(transaction.getDescription())
                                .currency(transaction.getCurrencyNumber())
                                .merchant(kapitalMerchantId).build()
                        ).build()
        );
    }

    public TKKPG_Request<StatusRequest> buildTkkpgStatusRequest(TransactionDto transaction) {
        var statusRequest = StatusRequest.builder()
                .language(Language.AZ.name())
                .operation(GET_ORDER_STATUS.getValue())
                .sessionID(transaction.getSessionId())
                .order(StatusRequestOrder.builder()
                        .merchant(kapitalMerchantId)
                        .orderID(transaction.getOrderId()).build()).build();

        return new TKKPG_Request<>(statusRequest);
    }
}
