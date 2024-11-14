package com.bvcott.booking.config;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity @Data 
public class GlobalFeeConfig {
    @Id private int id = 1;

    @Min(0)
    private double baseCharge;
    @Min(0)
    private double chargePerRoom;
    @Min(0)
    private double transactionFee;
}
