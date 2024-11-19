package com.bvcott.booking.exception.user;

@SuppressWarnings("serial")
public class ActionNotAllowedException extends RuntimeException {
    public ActionNotAllowedException(String message) {
        super(message);
    }
}
