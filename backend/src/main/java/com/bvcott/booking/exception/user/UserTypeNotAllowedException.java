package com.bvcott.booking.exception.user;

public class UserTypeNotAllowedException extends RuntimeException {
    public UserTypeNotAllowedException(String message) {
        super(message);
    }
}
