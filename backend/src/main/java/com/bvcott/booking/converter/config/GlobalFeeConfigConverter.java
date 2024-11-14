package com.bvcott.booking.converter.config;

import org.springframework.stereotype.Component;

import com.bvcott.booking.config.GlobalFeeConfig;
import com.bvcott.booking.dto.config.GlobalFeeConfigDTO;

@Component
public class GlobalFeeConfigConverter {
    public GlobalFeeConfigDTO toDto(GlobalFeeConfig entity) {
        return GlobalFeeConfigDTO
            .builder()
            .baseCharge(entity.getBaseCharge())
            .chargePerRoom(entity.getChargePerRoom())
            .transactionFee(entity.getTransactionFee())
            .build();
    }

    public GlobalFeeConfig toEntity(GlobalFeeConfigDTO dto) {
        GlobalFeeConfig entity = new GlobalFeeConfig();
        entity.setBaseCharge(dto.getBaseCharge());
        entity.setChargePerRoom(dto.getChargePerRoom());
        entity.setTransactionFee(dto.getTransactionFee());
        return entity;
    }
}
