package com.example.loanprovisioning.exception;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@ResponseStatus(HttpStatus.NO_CONTENT)
public class InvalidInputException extends CustomeException {
    private HttpStatus status;
    private String message;

    public InvalidInputException(HttpStatus status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }

    public InvalidInputException(String message) {
        super(message);
        this.message = message;
        this.status = HttpStatus.NO_CONTENT;
    }
}
