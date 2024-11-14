package com.bvcott.booking.service.user;

import java.time.LocalDateTime;
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
import com.bvcott.booking.model.Change;
import com.bvcott.booking.model.ChangeAction;
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

        log.debug("ID provided matches with Admin ID, creating Hotel Owner...");
        HotelOwner hotelOwnerToPersist = new HotelOwner();
        hotelOwnerToPersist.setUsername(dto.getUsername());
        hotelOwnerToPersist.setPassword(passwordGenerator.generateRandomString(10));
        
        log.debug("Checking if initial balance is below 5000...");
        if(dto.getBalance() < 5000) {
            throw new ActionNotAllowedException("Minimum allowed balance is of 5000 for a new Hotel Owner.");
        }

        log.debug("Initial balance equal or above the required amount, assigning to new Hotel Owner...");
        hotelOwnerToPersist.setBalance(dto.getBalance());

        log.debug("All values checked and assigned, persisting Hotel Owner...");
        User persistedHotelOwner = userRepo.save(hotelOwnerToPersist);

        log.debug("Creating new change log for admin-associated record...");
        Change change = new Change();
        change.setAction(ChangeAction.CREATE);
        change.setChangeDescription("Created new Hotel Owner - Details:\nID: " + persistedHotelOwner.getUserId() + "\nUsername: " + dto.getUsername() + "\nInitial Balance: " + dto.getBalance());
        change.setChangeTime(LocalDateTime.now());

        log.debug("Successfully created change record, associating with appropriate administrator.");
        Administrator admin = (Administrator) user;
        admin.getChanges().add(change);
        userRepo.save(admin);

        log.info("Hotel owner succesfully created, returning Hotel Owner DTO");
        return (HotelOwnerDTO) userConverter.toDto(persistedHotelOwner);
    }

    public void deleteHotelOwner(UUID adminId, UUID hotelOwnerId) {
        User foundAdmin = userRepo
            .findById(adminId)
            .orElseThrow(() -> new UserNotFoundException("User not found with the provided ID"));

        if(!(foundAdmin instanceof Administrator)) {
            throw new ActionNotAllowedException("Only Administrators can delete Hotel Owners");
        }

        User hotelOwnerToDelete = userRepo
            .findById(hotelOwnerId)
            .orElseThrow(() -> new UserNotFoundException("Hotel Owner not found. Can't delete"));

        userRepo.delete(hotelOwnerToDelete);

        Change change = new Change();
        change.setAction(ChangeAction.DELETE);
        change.setChangeDescription("Deleted Hotel Owner - Details:\nID: " + hotelOwnerId);
        change.setChangeTime(LocalDateTime.now());

        Administrator admin = (Administrator) foundAdmin;
        admin.getChanges().add(change);
    }

    public List<UserDTO> findAllHotelOwners() {
        return userRepo
            .findByUserType(HotelOwner.class)
            .stream()
            .map(userConverter::toDto)
            .collect(Collectors.toList());
    }

}
