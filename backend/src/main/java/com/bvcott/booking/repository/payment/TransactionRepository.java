package com.bvcott.booking.repository.payment;

import com.bvcott.booking.model.payment.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
}
