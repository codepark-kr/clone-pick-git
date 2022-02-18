package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class InvalidUserException extends UserException{

    private static final String CODE = "U0001";
    private static final String MESSAGE = "The user is not invalid.";

    public InvalidUserException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
