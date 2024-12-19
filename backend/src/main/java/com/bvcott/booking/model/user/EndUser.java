package com.bvcott.booking.model.user;

import com.bvcott.booking.model.payment.PaymentDetails;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public abstract class EndUser extends User {
    @OneToMany(cascade = CascadeType.ALL) @JsonManagedReference
    private List<PaymentDetails> paymentDetails = new ArrayList<>();

    public EndUser(String username, String password) {
        super(username, password);
    }

    public EndUser(String username, String password, List<PaymentDetails> paymentDetails) {
        super(username, password);
        this.paymentDetails = paymentDetails;
    }

    public void addPaymentDetails(PaymentDetails paymentDetails) {
        paymentDetails.setEndUser(this);
        this.paymentDetails.add(paymentDetails);
    }

    public void removePaymentDetails(PaymentDetails paymentDetails) {
        paymentDetails.setEndUser(null);
        this.paymentDetails.remove(paymentDetails);
    }
}
