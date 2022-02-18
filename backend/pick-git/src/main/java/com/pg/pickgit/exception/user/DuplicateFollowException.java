package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class DuplicateFollowException extends UserException{

    private static final String CODE = "U0002";
    private static final String MESSAGE = "Already following this user.";

    public DuplicateFollowException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
