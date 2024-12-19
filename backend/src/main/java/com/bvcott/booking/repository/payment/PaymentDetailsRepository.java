package com.bvcott.booking.repository.payment;

import com.bvcott.booking.model.payment.PaymentDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentDetailsRepository extends JpaRepository<PaymentDetails, UUID> {
}
