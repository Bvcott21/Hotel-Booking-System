package com.bvcott.booking.exception.user;

public class UserTypeNotRecognizedException extends RuntimeException {
    public UserTypeNotRecognizedException(String message) {
        super(message);
    }
}
