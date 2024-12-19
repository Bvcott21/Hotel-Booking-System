package com.bvcott.booking.model.user;

import com.bvcott.booking.model.booking.Booking;
import com.bvcott.booking.model.payment.BookingTransaction;
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

@Entity @Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Customer extends EndUser {
    @OneToMany(cascade = CascadeType.ALL) @JsonManagedReference
    List<Booking> bookings = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL)
    List<BookingTransaction> transactions = new ArrayList<>();

    public Customer(String username, String password) {
        super(username, password);
    }

    public Customer(String username, String password, List<PaymentDetails> paymentDetails) {
        super(username, password, paymentDetails);
    }

    public void addTransaction(BookingTransaction transaction) {
        transactions.add(transaction);
    }

    public void removeTransaction(BookingTransaction transaction) {
        transactions.remove(transaction);
    }

    public void addBooking(Booking booking) {
        bookings.add(booking);
        booking.setCustomer(this);
    }

    public void removeBooking(Booking booking) {
        bookings.remove(booking);
        booking.setCustomer(null);
    }
}
