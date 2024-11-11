package com.bvcott.booking.converter.user;

import org.springframework.stereotype.Component;

import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerDTO;
import com.bvcott.booking.exception.user.UserTypeNotRecognizedException;
import com.bvcott.booking.model.Administrator;
import com.bvcott.booking.model.HotelOwner;
import com.bvcott.booking.model.User;
import com.bvcott.booking.model.UserType;

@Component
public class UserConverter {
    public UserDTO toDto(User entity) {
        switch(entity.getClass().getSimpleName()) {
            case "Administrator":
                Administrator admin = (Administrator) entity;
                return UserDTO.builder()
                    .userType(UserType.ADMIN)
                    .userId(admin.getUserId())
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .build();
            case "HotelOwner":
                HotelOwner hotelOwner = (HotelOwner) entity;
                return HotelOwnerDTO.builder()
                    .userType(UserType.HOTEL_OWNER)
                    .userId(hotelOwner.getUserId())
                    .username(hotelOwner.getUsername())
                    .password(hotelOwner.getPassword())
                    .balance(hotelOwner.getBalance())
                    .build();
            default:
                throw new UserTypeNotRecognizedException("User type: " + entity.getClass().getSimpleName() + " not recognized");
        }
    }

    public User toEntity(UserDTO dto) {
        switch(dto.getUserType()) {
            case ADMIN:
                Administrator admin = new Administrator();
                admin.setUserId(dto.getUserId());
                admin.setUsername(dto.getUsername());
                admin.setPassword(dto.getPassword());
                return admin;
            case HOTEL_OWNER:
                HotelOwnerDTO ownerDto = (HotelOwnerDTO) dto;
                HotelOwner hotelOwner = new HotelOwner();
                hotelOwner.setUserId(ownerDto.getUserId());
                hotelOwner.setUsername(ownerDto.getUsername());
                hotelOwner.setPassword(ownerDto.getPassword());
                hotelOwner.setBalance(ownerDto.getBalance());
                return hotelOwner;
            default:
                throw new UserTypeNotRecognizedException("User type: " + dto.getUserType() + " not recognized");
        }

    }
}
