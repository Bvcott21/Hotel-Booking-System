package com.bvcott.booking.converter.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.bvcott.booking.converter.change.ChangeConverter;
import com.bvcott.booking.converter.hotel.HotelConverter;
import com.bvcott.booking.dto.change.ChangeDTO;
import com.bvcott.booking.dto.user.AdminDTO;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerDTO;
import com.bvcott.booking.exception.user.UserTypeNotRecognizedException;
import com.bvcott.booking.model.user.Administrator;
import com.bvcott.booking.model.user.Change;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.model.user.User;
import com.bvcott.booking.model.user.UserType;

import lombok.AllArgsConstructor;

@Component @AllArgsConstructor
public class UserConverter {
    private final ChangeConverter changeConverter;
    private final HotelConverter hotelConverter;
    
    public UserDTO toDto(User entity) {
        switch(entity.getClass().getSimpleName()) {
            case "Administrator":
                Administrator admin = (Administrator) entity;

                List<ChangeDTO> changeDtos = admin
                    .getChanges()
                    .stream()
                    .map(changeConverter::toDto)
                    .collect(Collectors.toList());
                    
                return AdminDTO.builder()
                    .userType(UserType.ADMIN)
                    .userId(admin.getUserId())
                    .username(admin.getUsername())
                    .password(admin.getPassword())
                    .changes(changeDtos)
                    .build();
                    
            case "HotelOwner":
                HotelOwner hotelOwner = (HotelOwner) entity;
                return HotelOwnerDTO.builder()
                    .userType(UserType.HOTEL_OWNER)
                    .userId(hotelOwner.getUserId())
                    .username(hotelOwner.getUsername())
                    .password(hotelOwner.getPassword())
                    .balance(hotelOwner.getBalance())
                    .hotels(hotelOwner
                        .getHotels()
                        .stream()
                        .map(hotelConverter::toDto)
                        .collect(Collectors.toList()))
                    .build();
            default:
                throw new UserTypeNotRecognizedException("User type: " + entity.getClass().getSimpleName() + " not recognized");
        }
    }

    public User toEntity(UserDTO dto) {
        switch(dto.getUserType()) {
            case ADMIN:
                AdminDTO adminDto = (AdminDTO) dto;

                List<Change> changes = adminDto
                    .getChanges()
                    .stream()
                    .map(changeConverter::toEntity)
                    .collect(Collectors.toList());

                Administrator admin = new Administrator();
                admin.setUserId(dto.getUserId());
                admin.setUsername(dto.getUsername());
                admin.setPassword(dto.getPassword());
                admin.setChanges(changes);

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
