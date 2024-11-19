package com.bvcott.booking.model.user;

import java.util.ArrayList;
import java.util.List;

import com.bvcott.booking.model.hotel.Hotel;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity @Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class HotelOwner extends User {
    double balance;
    
    public HotelOwner(String username, String password, double balance) {
    	super(username, password);
    	this.balance = balance;
    }
    @OneToMany(cascade = CascadeType.ALL) 
    List<Hotel> hotels = new ArrayList<>();

    public void addHotel(Hotel hotel) {
        hotels.add(hotel);
    }

    public void removeHotel(Hotel hotel) {
        hotels.remove(hotel);
    }
}
