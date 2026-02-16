package com.example.secureuserauth.exception;

public class UserException extends RuntimeException{

    public UserException() {
    }

    public UserException(String message) {
        super(message);
    }

    public UserException(Throwable cause) {
        super(cause);
    }
    
}
