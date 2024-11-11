package com.bvcott.booking.dto.user;

import java.util.List;

import com.bvcott.booking.dto.change.ChangeDTO;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class AdminDTO extends UserDTO{
    private List<ChangeDTO> changes;
}
