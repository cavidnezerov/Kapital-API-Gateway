package com.kapital.onlinepaymentgateway.kapitalecommerce.entity;

import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.KapitalStatus;
import com.kapital.onlinepaymentgateway.kapitalecommerce.enumaration.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transaction")
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "description")
    private String description;
    @Column(name = "order_id")
    private String orderId;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "currency_number")
    private Integer currencyNumber;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    @Column(name = "kapital_status")
    @Enumerated(EnumType.STRING)
    private KapitalStatus kapitalStatus;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;
    @PreUpdate
    private void onUpdate(){
        this.updatedOn = LocalDateTime.now();
    }
}
