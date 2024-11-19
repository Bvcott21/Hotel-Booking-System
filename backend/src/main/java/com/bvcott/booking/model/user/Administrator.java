package com.bvcott.booking.model.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity @Data @EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Administrator extends User {
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Change> changes = new ArrayList<>();
    
    public Administrator(String username, String password) {
    	super(username, password);
    }
}
