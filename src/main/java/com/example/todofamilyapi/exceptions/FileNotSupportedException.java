package com.example.todofamilyapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
public class FileNotSupportedException extends RuntimeException {

    public FileNotSupportedException(final String msg) {
        super(msg);
    }

    public FileNotSupportedException() {
        super("File Corrupted");
    }

}
