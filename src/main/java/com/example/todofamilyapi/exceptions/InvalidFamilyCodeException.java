package com.example.todofamilyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class InvalidFamilyCodeException extends RuntimeException {

    public InvalidFamilyCodeException(final String msg) {
        super(msg);
    }
}
