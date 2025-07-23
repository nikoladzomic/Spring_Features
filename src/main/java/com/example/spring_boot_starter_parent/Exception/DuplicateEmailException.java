package com.example.spring_boot_starter_parent.Exception;

public class DuplicateEmailException extends RuntimeException{

    public DuplicateEmailException(String message) {
        super(message);
    }

    public DuplicateEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public static DuplicateEmailException fromEmail(String email) {
        return new DuplicateEmailException("Client with email " + email + " already exists");
    }
}
