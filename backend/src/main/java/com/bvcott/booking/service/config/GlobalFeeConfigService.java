package com.bvcott.booking.service.config;

import java.time.LocalDateTime;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bvcott.booking.config.GlobalFeeConfig;
import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.model.user.Administrator;
import com.bvcott.booking.model.user.Change;
import com.bvcott.booking.model.user.ChangeAction;
import com.bvcott.booking.repository.config.GlobalFeeConfigRepository;
import com.bvcott.booking.repository.user.AdministratorRepository;

import lombok.AllArgsConstructor;

@Service @AllArgsConstructor
public class GlobalFeeConfigService {
    private final Logger log = LoggerFactory.getLogger(GlobalFeeConfigService.class);
    private final GlobalFeeConfigRepository feeRepo;
    private final AdministratorRepository adminRepo;

    public GlobalFeeConfig getCurrentFees(UUID adminId) {
        log.info("getCurrentFees triggered, retrieving...");
        adminRepo
            .findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found, can't update Global Fees"));

        return feeRepo.findById(1).get();
    }

    public GlobalFeeConfig updateFees(UUID adminId, GlobalFeeConfig newFees) {
        log.info("updateFees triggered with values adminId: {}, newFees: {}", adminId, newFees);
        GlobalFeeConfig currentFees = getCurrentFees(adminId);
        
        Administrator admin = adminRepo
            .findById(adminId)
            .orElseThrow(() -> new ResourceNotFoundException("Admin not found, can't update Global Fees"));

        if(currentFees.getBaseCharge() != newFees.getBaseCharge()) {
            log.debug("Base charge has changed.");
            currentFees.setBaseCharge(newFees.getBaseCharge());
        } 
        if(currentFees.getChargePerRoom() != newFees.getChargePerRoom()) {
            log.debug("Charge per room has changed.");
            currentFees.setChargePerRoom(newFees.getChargePerRoom());
        } 
        if(currentFees.getTransactionFee() != newFees.getTransactionFee()) {
            log.debug("Transaction Fee has changed.");
            currentFees.setTransactionFee(newFees.getTransactionFee());
        }

        log.debug("Persisting changes...");
        currentFees = feeRepo.save(currentFees);

        log.debug("logging change to admin");
        Change change = new Change();
        change.setAction(ChangeAction.UPDATE);
        change.setChangeDescription("Global Fees updated, details:\n"
            + "Base Charge: " + currentFees.getBaseCharge() + "\n"
            + "Charge per Room: " + currentFees.getChargePerRoom() + "\n"
            + "Transaction Fees: " + currentFees.getTransactionFee());
        change.setChangeTime(LocalDateTime.now());

        admin.getChanges().add(change);
        adminRepo.save(admin);

        log.info("Changes to Global Fees persisted");
        return currentFees;
    }
}
