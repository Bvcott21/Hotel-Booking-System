package com.bvcott.booking.dto.user;

import com.bvcott.booking.model.UserType;
import java.util.UUID;
import lombok.Data;
import lombok.Builder;



@Data @Builder
public class UserDTO {
    private UUID userId;
    private UserType userType;
    private String username;
    private String password;
}
