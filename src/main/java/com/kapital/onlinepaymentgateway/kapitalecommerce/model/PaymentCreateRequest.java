package com.kapital.onlinepaymentgateway.kapitalecommerce.model;

import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.Language;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCreateRequest {
    BigDecimal amount;
    int currencyNumber;
    Language language;
    String description;
}
