package com.bvcott.booking.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data
@NoArgsConstructor
@Table(name = "BOOKING_SYSTEM_USER")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID userId;
    private String username;
    private String password;
}
