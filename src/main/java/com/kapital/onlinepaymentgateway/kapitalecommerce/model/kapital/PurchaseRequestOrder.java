package com.kapital.onlinepaymentgateway.kapitalecommerce.model.kapital;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JacksonXmlRootElement
public class PurchaseRequestOrder {
    @JacksonXmlProperty(localName = "OrderType")
    private String orderType;
    @JacksonXmlProperty(localName = "Merchant")
    private String merchant;
    @JacksonXmlProperty(localName = "Amount")
    private BigDecimal amount;
    @JacksonXmlProperty(localName = "Currency")
    private Integer currency;
    @JacksonXmlProperty(localName = "Description")
    private String description;
    @JacksonXmlProperty(localName = "ApproveURL")
    private String approveURL;
    @JacksonXmlProperty(localName = "CancelURL")
    private String cancelURL;
    @JacksonXmlProperty(localName = "DeclineURL")
    private String declineURL;
}
