package com.kapital.onlinepaymentgateway.kapitalecommerce.repository;

import com.kapital.onlinepaymentgateway.kapitalecommerce.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
