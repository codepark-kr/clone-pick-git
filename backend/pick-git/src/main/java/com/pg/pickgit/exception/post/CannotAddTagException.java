package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class CannotAddTagException extends PostException{

    private static final String CODE = "P0001";
    private static final String MESSAGE = "Adding tag has been failed.";

    public CannotAddTagException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
