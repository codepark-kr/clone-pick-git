package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class InvalidFollowException extends UserException{

    private static final String CODE = "U0003";
    private static final String MESSAGE = "The follow doesn't exists.";

    public InvalidFollowException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
