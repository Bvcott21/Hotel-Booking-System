package com.bvcott.booking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bvcott.booking.exception.general.ResourceNotFoundException;
import com.bvcott.booking.exception.user.ActionNotAllowedException;
import com.bvcott.booking.exception.user.UserTypeNotAllowedException;
import com.bvcott.booking.exception.user.UserTypeNotRecognizedException;

import lombok.Getter;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return exceptionHandler(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserTypeNotAllowedException.class)
    public ResponseEntity<Object> handleUserTypeNotAllowedException(UserTypeNotAllowedException ex) {
        return exceptionHandler(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(UserTypeNotRecognizedException.class)
    public ResponseEntity<Object> handleUserTypeNotRecognizedException(UserTypeNotRecognizedException ex) {
        return exceptionHandler(ex, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ActionNotAllowedException.class)
    public ResponseEntity<Object> handleActionNotAllowedException(ActionNotAllowedException ex) {
        return exceptionHandler(ex, HttpStatus.FORBIDDEN);
    }

    private static ResponseEntity<Object> exceptionHandler(RuntimeException ex, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(status, ex.getMessage());
        return new ResponseEntity<>(errorResponse, status);
    }

    @Getter
    private static class ErrorResponse {
        private int status;
        private String error;
        private String message;
        
        public ErrorResponse(HttpStatus status, String message) {
            this.status = status.value();
            this.error = status.getReasonPhrase();
            this.message = message;
        }
        
    }
}
