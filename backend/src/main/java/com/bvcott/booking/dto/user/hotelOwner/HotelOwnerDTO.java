package com.bvcott.booking.dto.user.hotelOwner;

import com.bvcott.booking.dto.user.UserDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data @SuperBuilder @EqualsAndHashCode(callSuper = true)
public class HotelOwnerDTO extends UserDTO{
    private double balance;
}
