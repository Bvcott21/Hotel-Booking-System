package com.bvcott.booking.model.address;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable @Data
public class Address {
    private String streetNumber1;
    private String streetNumber2;
    private String postCode;
    private String city;
    private String country;
}
