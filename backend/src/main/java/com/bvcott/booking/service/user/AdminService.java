package com.bvcott.booking.service.user;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.bvcott.booking.dto.user.hotelOwner.HotelOwnerCreateDTO;
import com.bvcott.booking.repository.booking.BookingRepository;
import com.bvcott.booking.repository.hotel.HotelRepository;
import com.bvcott.booking.repository.hotel.HotelRoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.model.user.Administrator;
import com.bvcott.booking.model.user.Change;
import com.bvcott.booking.model.user.ChangeAction;
import com.bvcott.booking.model.user.HotelOwner;
import com.bvcott.booking.repository.UserRepository;
import com.bvcott.booking.repository.user.AdministratorRepository;
import com.bvcott.booking.repository.user.HotelOwnerRepository;
import com.bvcott.booking.utility.RandomStringGenerator;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class AdminService {
    private static final Logger log = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepo;
    private final AdministratorRepository adminRepo;
    private final HotelOwnerRepository hotelOwnerRepo;
    private final HotelRepository hotelRepo;
    private final HotelRoomRepository hotelRoomRepo;
    private final BookingRepository bookingRepo;
    private final RandomStringGenerator passwordGenerator;

    public HotelOwner createHotelOwner(UUID adminId, HotelOwnerCreateDTO dto) {
        log.info("createHotelOwner triggered with values ID: {} | dto: {}", adminId, dto);
        
        log.debug("checking if ID provided belongs to an admin");
        Administrator admin = adminRepo
            .findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Could not find administrator with the provided id: " + adminId));

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
        HotelOwner persistedHotelOwner = hotelOwnerRepo.save(hotelOwnerToPersist);

        log.debug("Creating new change log for admin-associated record...");
        Change change = new Change();
        change.setAction(ChangeAction.CREATE);
        change.setChangeDescription("Created new Hotel Owner - Details:\nID: " + persistedHotelOwner.getUserId() + "\nUsername: " + dto.getUsername() + "\nInitial Balance: " + dto.getBalance());
        change.setChangeTime(LocalDateTime.now());

        log.debug("Successfully created change record, associating with appropriate administrator.");
        admin.getChanges().add(change);
        adminRepo.save(admin);

        log.info("Hotel owner succesfully created, returning created Hotel Owner");
        return persistedHotelOwner;
    }

    public void deleteHotelOwner(UUID adminId, UUID hotelOwnerId) {
        log.info("deleteHotelOwner triggered with values: Admin ID: {}, Hotel Owner ID: {}", adminId, hotelOwnerId);

        log.debug("Finding admin by ID...");
        Administrator admin = adminRepo
            .findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with the provided ID"));

            log.debug("Admin found, finding Hotel Owner by ID...");
        HotelOwner hotelOwnerToDelete = hotelOwnerRepo
            .findById(hotelOwnerId)
            .orElseThrow(() -> new ResourceNotFoundException("Hotel Owner not found. Can't delete"));

        log.debug("Hotel owner found, deleting associated hotels...");
        hotelOwnerToDelete
                .getHotels()
                        .forEach(hotel -> {
                            log.debug("Deleting associated bookings for hotel: {}", hotel.getHotelId());
                            bookingRepo.deleteAll(bookingRepo.findByHotel_HotelId(hotel.getHotelId()));

                            log.debug("Deleting associated hotel rooms...");
                            hotelRoomRepo.deleteAll(hotel.getRooms());

                            log.debug("Deleting hotel...");
                            hotelRepo.delete(hotel);
                        });

        log.debug("Deteling hotel owner...");
        userRepo.delete(hotelOwnerToDelete);

        log.debug("Hotel owner deleted successfully, logging activity on Administrator...");
        Change change = new Change();
        change.setAction(ChangeAction.DELETE);
        change.setChangeDescription("Deleted Hotel Owner - Details:\nID: " + hotelOwnerId);
        change.setChangeTime(LocalDateTime.now());


        admin.getChanges().add(change);
        adminRepo.save(admin);
        log.debug("Change logged to admin.");
    }

    public List<HotelOwner> findAllHotelOwners() {
        log.info("findAllHotelOwners triggered");
        return hotelOwnerRepo.findAll();
    }

    public Administrator findById(UUID adminId) {
        log.info("findById triggered with ID {}", adminId);
        return adminRepo
            .findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found with the provided ID"));
    } 

}
