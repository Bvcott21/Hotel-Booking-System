package com.bvcott.booking.model.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.model.hotel.Hotel;
import com.bvcott.booking.model.hotel.HotelRoom;

import com.bvcott.booking.model.payment.BookingTransaction;
import com.bvcott.booking.model.user.Customer;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity @Data
public class Booking {
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID bookingId;

        @ManyToOne @JsonBackReference
        private Hotel hotel;

        @ManyToMany @JsonBackReference
        private List<HotelRoom> rooms = new ArrayList<>();

        @NotNull
        private LocalDate checkin;

        @NotNull
        private LocalDate checkout;

        @NotNull
        private double price;

        @ManyToOne @JsonBackReference
        private Customer customer;

        @ManyToOne(cascade = CascadeType.ALL) @JsonBackReference
        private BookingTransaction bookingTransaction;
}
