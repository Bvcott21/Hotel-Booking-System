package com.bvcott.booking.model.payment;

import com.bvcott.booking.model.address.Address;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity @Data @NoArgsConstructor
@AllArgsConstructor
public class CardDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID cardDetailsId;
    private String cardNumber;
    private String CVC;
    private LocalDate issueDate;
    private LocalDate expiryDate;

    @Embedded
    private Address address;

    public CardDetails(String cardNumber, String CVC, LocalDate issueDate, LocalDate expiryDate, Address address) {
        this.cardNumber = cardNumber;
        this.CVC = CVC;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.address = address;
    }
}
