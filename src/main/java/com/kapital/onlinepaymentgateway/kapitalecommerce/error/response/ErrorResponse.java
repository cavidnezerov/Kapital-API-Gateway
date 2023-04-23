package com.kapital.onlinepaymentgateway.kapitalecommerce.error.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private String title;
    private String message;
}
