package com.bvcott.booking.config;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Entity @Data 
public class GlobalFeeConfig {
    @Id private int id = 1;

    @Min(0)
    private BigDecimal baseCharge;
    @Min(0)
    private BigDecimal chargePerRoom;
    @Min(0)
    private BigDecimal transactionFee;
}
