package com.bvcott.booking.service.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.user.UserConverter;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.exception.user.UserNotFoundException;
import com.bvcott.booking.model.Administrator;
import com.bvcott.booking.model.HotelOwner;
import com.bvcott.booking.model.User;
import com.bvcott.booking.model.UserType;
import com.bvcott.booking.repository.UserRepository;

@Service
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    @Autowired UserRepository userRepo;
    @Autowired UserConverter userConverter;

    public UserDTO createHotelOwner(UUID adminId, UserDTO dto) {
        log.info("createHotelOwner triggered with values ID: {} | dto: {}", adminId, dto);
        log.debug("checking if ID provided belongs to an admin");

        User user = userRepo
            .findById(adminId)
            .orElseThrow(() -> new UserNotFoundException("Could not find user with the provided id: " + adminId));
        
        if(!(user instanceof Administrator)) {
            throw new ActionNotAllowedException("Only Administrators can create new Hotel Owners");
        }

        if(dto.getUserType() != UserType.HOTEL_OWNER) {
            throw new ActionNotAllowedException("Not creating an Hotel Owner, if trying to create a different type of user please refer to the appropriate form.");
        }

        HotelOwner hotelOwnerToPersist = (HotelOwner) userConverter.toEntity(dto);
        User persistedHotelOwner = userRepo.save(hotelOwnerToPersist);

        return userConverter.toDto(persistedHotelOwner);
    }

    public List<UserDTO> findAllHotelOwners() {
        return userRepo
            .findByUserType(HotelOwner.class)
            .stream()
            .map(user -> userConverter.toDto(user))
            .collect(Collectors.toList());
    }
}
