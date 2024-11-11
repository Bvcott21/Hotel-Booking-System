package com.bvcott.booking.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.Change;

public interface ChangeRepository extends JpaRepository<Change, UUID>{

}
