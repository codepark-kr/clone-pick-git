package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class TagFormatException extends PostException{

    private static final String CODE = "F0003";
    private static final String MESSAGE = "The format of tag is not applicable.";

    public TagFormatException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
