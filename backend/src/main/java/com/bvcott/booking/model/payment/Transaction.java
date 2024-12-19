package com.bvcott.booking.model.payment;

import com.bvcott.booking.model.booking.Booking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity @Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID transactionId;
    @ManyToOne @JsonManagedReference
    private PaymentDetails paymentDetails;

    @Enumerated(EnumType.STRING)
    private TransactionStatus status;

    private LocalDateTime createdAt;

    private double total;

    public Transaction(PaymentDetails paymentDetails, TransactionStatus status, LocalDateTime createdAt) {
        this.paymentDetails = paymentDetails;
        this.status = status;
        this.createdAt = createdAt;
    }
}
