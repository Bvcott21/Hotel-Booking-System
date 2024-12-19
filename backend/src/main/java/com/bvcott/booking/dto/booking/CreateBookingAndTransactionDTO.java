package com.bvcott.booking.dto.booking;

import com.bvcott.booking.model.payment.PaymentDetails;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Builder @Data
public class CreateBookingAndTransactionDTO {
    private List<BookingCreateDTO> bookings;
    private UUID paymentDetailsId;
}
