package com.bvcott.booking.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bvcott.booking.model.user.User;

public interface UserRepository extends JpaRepository<User, UUID> {
    @Query("SELECT u FROM User u WHERE TYPE(u) = :type")
    List<User> findByUserType(Class<? extends User> type);
}
