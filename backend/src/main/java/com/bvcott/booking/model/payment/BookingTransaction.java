package com.bvcott.booking.model.payment;

import com.bvcott.booking.model.booking.Booking;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity @Data @NoArgsConstructor
public class BookingTransaction extends Transaction {
    @OneToMany(mappedBy = "bookingTransaction", cascade = CascadeType.ALL) @JsonBackReference
    private List<Booking> bookings = new ArrayList<>();

    public BookingTransaction(PaymentDetails paymentDetails, TransactionStatus status, LocalDateTime createdAt, List<Booking> bookings) {
        super(paymentDetails, status, createdAt);
        this.bookings = bookings;

        double total = 0.0;
        for(Booking booking : bookings){
            total += booking.getPrice();
        }
        this.setTotal(total);
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
        booking.setBookingTransaction(this);
        this.setTotal(this.getTotal() + booking.getPrice());
    }
}
