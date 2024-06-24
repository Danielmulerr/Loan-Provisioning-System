package com.example.loanprovisioning.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is a custom exception which is parent for all other exceptions we create
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomeException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CustomeException(String message) {
        super(message);
    }
}
