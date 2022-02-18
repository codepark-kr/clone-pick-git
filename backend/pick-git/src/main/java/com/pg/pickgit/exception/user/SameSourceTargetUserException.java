package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class SameSourceTargetUserException extends UserException{

    private static final String CODE = "U0004";
    private static final String MESSAGE = "The targeted user is same with source.";

    public SameSourceTargetUserException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }

}
