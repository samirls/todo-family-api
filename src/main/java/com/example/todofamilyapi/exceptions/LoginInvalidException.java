package com.example.todofamilyapi.exceptions;

public class LoginInvalidException extends RuntimeException {
    public LoginInvalidException(String s) {
        super(s);
    }
}
