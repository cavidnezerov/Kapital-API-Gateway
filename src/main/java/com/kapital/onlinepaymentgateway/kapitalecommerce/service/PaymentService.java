package com.kapital.onlinepaymentgateway.kapitalecommerce.service;

import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.KapitalStatus;
import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.TransactionStatus;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.PaymentRequestException;
import com.kapital.onlinepaymentgateway.kapitalecommerce.error.response.ResponseMessage;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.DeterminateResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.PaymentCreateRequest;
import com.kapital.onlinepaymentgateway.kapitalecommerce.model.PaymentCreateResponse;
import com.kapital.onlinepaymentgateway.kapitalecommerce.util.BuilderUtil;
import com.kapital.onlinepaymentgateway.kapitalecommerce.util.HttpUtil;
import com.kapital.onlinepaymentgateway.kapitalecommerce.util.XmlUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    @Value("${kapital.service.url}")
    private String kapitalServiceUrl;
    private final TransactionService transactionService;
    private final BuilderUtil builderUtil;
    private final HttpUtil httpUtil;
    private final XmlUtil xmlUtil;
    @Transactional
    @SneakyThrows
    public PaymentCreateResponse createPayment(PaymentCreateRequest request) {
        var transaction = builderUtil.buildPendingTransaction(request);
        transaction = transactionService.save(transaction);
        var tkkpgRequest = builderUtil.buildTkkpgPurchaseRequest(transaction);

        var content = httpUtil
                .sendPostRequest(kapitalServiceUrl, new StringEntity(xmlUtil.write(tkkpgRequest)))
                .getEntity()
                .getContent();

        var purchaseResponse = xmlUtil.readPurchaseResponse(content).getResponse();

        if (!purchaseResponse.getStatus().equals(KapitalStatus.SUCCESS.getValue())) {
            throw new PaymentRequestException(ResponseMessage.KAPITAL_REQUEST_FAILED, purchaseResponse.toString());
        }

        var order = purchaseResponse.getOrder();

        transaction.setOrderId(order.getOrderID());
        transaction.setSessionId(order.getSessionID());
        transactionService.save(transaction);

        return new PaymentCreateResponse(order.getUrl() + "?ORDERID=" + order.getOrderID() + "&SESSIONID=" + order.getSessionID());
    }

    @Transactional
    @SneakyThrows
    public DeterminateResponse determinatePayment(Long id, String status) {
        var transaction = transactionService.findById(id);

        var tkkpgRequest = builderUtil.buildTkkpgStatusRequest(transaction);

        var content = httpUtil
                .sendPostRequest(kapitalServiceUrl, new StringEntity(xmlUtil.write(tkkpgRequest)))
                .getEntity()
                .getContent();

        var statusResponse = xmlUtil.readStatusResponse(content).getResponse();

        var response = new DeterminateResponse();
        if (statusResponse.getStatus().equals(KapitalStatus.SUCCESS.getValue())) {
            response.setRequestStatus(true);
            if ("APPROVE".equals(status) && statusResponse.getOrder().getOrderStatus().equals("APPROVED")) {
                transaction.setStatus(TransactionStatus.SUCCESS);
                transaction = transactionService.save(transaction);
                log.info("---------------------------> Payment Succeed, transaction: " + transaction.toString());
                response.setPaymentStatus(true);
            } else {
                transaction.setStatus(TransactionStatus.FAILED);
                transaction = transactionService.save(transaction);
                log.error(ResponseMessage.KAPITAL_PAYMENT_FAILED + " redirect status: " + status + ", transaction: " + transaction);
                log.error("response: " + status);
                response.setPaymentStatus(false);
            }
        } else {
            //In this case you need to check if the request was sent in the correct form
            response.setRequestStatus(false);
            transaction.setStatus(TransactionStatus.EXCEPTIONAL);
            transactionService.save(transaction);
            log.error(ResponseMessage.KAPITAL_PAYMENT_FAILED + " redirect status: " + status + ", transaction: " + transaction);
            log.error("response: " + statusResponse);
        }

        return response;
    }
}
