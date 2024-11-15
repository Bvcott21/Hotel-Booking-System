package com.bvcott.booking.model.user;

import java.util.ArrayList;
import java.util.List;

import com.bvcott.booking.model.hotel.Hotel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity @Data @EqualsAndHashCode(callSuper = false)
public class HotelOwner extends User {
    double balance;
    
    @OneToMany(cascade = CascadeType.ALL) 
    List<Hotel> hotels = new ArrayList<>();

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public void removeHotel(Hotel hotel) {
        hotels.remove(hotel);
    }
}
