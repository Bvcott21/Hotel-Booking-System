package com.bvcott.booking.converter.change;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.change.ChangeDTO;
import com.bvcott.booking.model.Change;

@Component
public class ChangeConverter {
    public ChangeDTO toDto(Change entity) {
        return ChangeDTO.builder()
            .changeId(entity.getChangeId())
            .action(entity.getAction())
            .changeDescription(entity.getChangeDescription())
            .changeTime(entity.getChangeTime())
            .build();
    }

    public Change toEntity(ChangeDTO dto) {
        Change entity = new Change();
        entity.setChangeId(dto.getChangeId());
        entity.setAction(dto.getAction());
        entity.setChangeDescription(dto.getChangeDescription());
        entity.setChangeTime(dto.getChangeTime());
        return entity;
    }
}