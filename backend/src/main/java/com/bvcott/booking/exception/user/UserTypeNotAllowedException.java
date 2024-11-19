package com.bvcott.booking.exception.user;

@SuppressWarnings("serial")
public class UserTypeNotAllowedException extends RuntimeException {
    public UserTypeNotAllowedException(String message) {
        super(message);
    }
}
