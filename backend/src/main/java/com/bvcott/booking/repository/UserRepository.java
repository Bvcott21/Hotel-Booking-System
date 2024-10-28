package com.bvcott.booking.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bvcott.booking.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
