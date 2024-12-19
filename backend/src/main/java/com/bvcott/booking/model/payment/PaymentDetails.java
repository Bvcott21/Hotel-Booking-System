package com.bvcott.booking.model.payment;

import com.bvcott.booking.model.user.EndUser;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity @Data @NoArgsConstructor
public class PaymentDetails {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID paymentDetailsId;

    @ManyToOne @JsonBackReference
    private EndUser endUser;

    @OneToOne(cascade = CascadeType.ALL) @JsonManagedReference
    private CardDetails cardDetails;

    public PaymentDetails(CardDetails cardDetails) {
        this.cardDetails = cardDetails;
    }

    public PaymentDetails(EndUser endUser, CardDetails cardDetails) {
        this.endUser = endUser;
        this.cardDetails = cardDetails;
    }
}
