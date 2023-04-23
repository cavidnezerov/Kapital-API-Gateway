package com.kapital.onlinepaymentgateway.kapitalecommerce.model;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(content = JsonInclude.Include.NON_NULL)
public class PaymentCreateResponse {
    String url;
}
