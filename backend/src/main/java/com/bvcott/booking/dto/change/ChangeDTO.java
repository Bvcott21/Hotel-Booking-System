package com.bvcott.booking.dto.change;

import java.time.LocalDateTime;
import java.util.UUID;

import com.bvcott.booking.model.ChangeAction;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ChangeDTO {
    private UUID changeId;
    private ChangeAction action;
    private String changeDescription;
    private LocalDateTime changeTime;
}
