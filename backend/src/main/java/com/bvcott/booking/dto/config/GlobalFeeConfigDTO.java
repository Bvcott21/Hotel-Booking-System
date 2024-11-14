package com.bvcott.booking.dto.config;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GlobalFeeConfigDTO {
    private int id;
    private double baseCharge;
    private double chargePerRoom;
    private double transactionFee;
}
