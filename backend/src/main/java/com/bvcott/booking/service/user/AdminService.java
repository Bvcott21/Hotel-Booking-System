package com.bvcott.booking.service.user;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.converter.user.UserConverter;
import com.bvcott.booking.dto.user.UserDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerCreateDTO;
import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerDTO;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.exception.user.UserNotFoundException;
import com.bvcott.booking.model.Administrator;
import com.bvcott.booking.model.HotelOwner;
import com.bvcott.booking.model.User;
import com.bvcott.booking.repository.UserRepository;
import com.bvcott.booking.utility.RandomStringGenerator;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepo;
    private final UserConverter userConverter;
    private final RandomStringGenerator passwordGenerator;

    public HotelOwnerDTO createHotelOwner(UUID adminId, HotelOwnerCreateDTO dto) {
        log.info("createHotelOwner triggered with values ID: {} | dto: {}", adminId, dto);
        log.debug("checking if ID provided belongs to an admin");

        User user = userRepo
            .findById(adminId)
            .orElseThrow(() -> new UserNotFoundException("Could not find user with the provided id: " + adminId));
        
        if(!(user instanceof Administrator)) {
            throw new ActionNotAllowedException("Only Administrators can create new Hotel Owners");
        }

        HotelOwner hotelOwnerToPersist = new HotelOwner();
        hotelOwnerToPersist.setUsername(dto.getUsername());
        hotelOwnerToPersist.setPassword(passwordGenerator.generateRandomString(10));
        
        if(dto.getBalance() < 5000) {
            throw new ActionNotAllowedException("Minimum allowed balance is of 5000 for a new Hotel Owner.");
        }

        hotelOwnerToPersist.setBalance(dto.getBalance());

        User persistedHotelOwner = userRepo.save(hotelOwnerToPersist);

        return (HotelOwnerDTO) userConverter.toDto(persistedHotelOwner);
    }

    public List<UserDTO> findAllHotelOwners() {
        return userRepo
            .findByUserType(HotelOwner.class)
            .stream()
            .map(userConverter::toDto)
            .collect(Collectors.toList());
    }

}
