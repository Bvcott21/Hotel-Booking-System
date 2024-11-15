package com.bvcott.booking.model.user;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity @Data @EqualsAndHashCode(callSuper = false)
public class HotelOwner extends User {
    double balance;
}
