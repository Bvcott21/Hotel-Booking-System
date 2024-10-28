package com.bvcott.booking.model;

import lombok.Getter;

@Getter
public enum ChangeAction {
    CREATE("Create a new item", "create"),
    UPDATE("Update an item", "update"),
    DELETE("Delete an item", "delete");

    private final String description;
    private final String action;
    
    private ChangeAction(String description, String action) {
        this.description = description;
        this.action = action;
    }
    
}
