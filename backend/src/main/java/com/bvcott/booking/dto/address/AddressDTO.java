package com.bvcott.booking.dto.address;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class AddressDTO {
    private String streetNumber1;
    private String streetNumber2;
    private String postCode;
    private String city;
    private String country;
}
