package com.kapital.onlinepaymentgateway.kapitalecommerce.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto {
    Long id;
    String description;
    String orderId;
    String sessionId;
    BigDecimal amount;
    Integer currencyNumber;
    TransactionStatus status;
    LocalDateTime createdOn;
    LocalDateTime updatedOn;
}
