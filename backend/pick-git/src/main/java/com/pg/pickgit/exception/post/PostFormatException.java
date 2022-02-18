package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class PostFormatException extends PostException{

    private static final String CODE = "F0004";
    private static final String MESSAGE = "The length of post is not applicable.";

    public PostFormatException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
