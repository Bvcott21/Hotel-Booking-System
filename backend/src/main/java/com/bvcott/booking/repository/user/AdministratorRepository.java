package com.bvcott.booking.repository.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.Administrator;

public interface AdministratorRepository extends JpaRepository<Administrator, UUID>{

}
