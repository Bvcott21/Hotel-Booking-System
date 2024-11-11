package com.bvcott.booking.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity @Data @EqualsAndHashCode(callSuper = false)
public class Administrator extends User {
    //private List<Change> changes = new ArrayList<>();
