package com.bvcott.booking.model.address;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable @Data @NoArgsConstructor @AllArgsConstructor
public class Address {
    private String streetNumber1;
    private String streetNumber2;
    private String postCode;
    private String city;
    private String country;
}
