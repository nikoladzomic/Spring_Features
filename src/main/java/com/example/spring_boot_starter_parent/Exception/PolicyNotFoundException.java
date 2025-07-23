package com.example.spring_boot_starter_parent.Exception;

public class PolicyNotFoundException extends RuntimeException {

    public PolicyNotFoundException(String message) {
        super(message);
    }

    public PolicyNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PolicyNotFoundException(Long policyId) {
        super("Policy not found with ID: " + policyId);
    }
}
