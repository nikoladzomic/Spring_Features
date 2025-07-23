package com.example.spring_boot_starter_parent.Exception;


public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException (String message) {
        super(message);
    }

    public ClientNotFoundException (String message, Throwable cause) {
        super(message, cause);
    }

    public ClientNotFoundException (Long clientId) {
        super("Client not found with ID: "+ clientId);
    }

}
