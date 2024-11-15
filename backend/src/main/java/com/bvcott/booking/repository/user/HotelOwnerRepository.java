package com.bvcott.booking.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.user.HotelOwner;

public interface HotelOwnerRepository extends JpaRepository<HotelOwner, UUID> {

}
