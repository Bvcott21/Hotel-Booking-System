package com.bvcott.booking.model.user;

import com.bvcott.booking.model.payment.PaymentDetails;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Customer extends EndUser {
    public Customer(String username, String password) {
        super(username, password);
    }

    public Customer(String username, String password, List<PaymentDetails> paymentDetails) {
        super(username, password, paymentDetails);
    }
}
