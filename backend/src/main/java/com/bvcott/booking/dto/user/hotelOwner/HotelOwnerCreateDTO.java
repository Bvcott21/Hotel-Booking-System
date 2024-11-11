package com.bvcott.booking.dto.user.hotelOwner;

import lombok.Builder;
import lombok.Data;

@Builder @Data
public class HotelOwnerCreateDTO {
    private String username;
    private double balance;
}
