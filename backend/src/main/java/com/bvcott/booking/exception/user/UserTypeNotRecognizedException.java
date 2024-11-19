package com.bvcott.booking.exception.user;

@SuppressWarnings("serial")
public class UserTypeNotRecognizedException extends RuntimeException {
    public UserTypeNotRecognizedException(String message) {
        super(message);
    }
}
