package com.bvcott.booking.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity @Data @NoArgsConstructor
public class Change {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID changeId;
    
    @Enumerated(EnumType.STRING) 
    private ChangeAction action;
    
    private String changeDescription;

    public Change(ChangeAction action, String changeDescription) {
        this.action = action;
        this.changeDescription = changeDescription;
    }
    
}
