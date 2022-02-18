package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class CommentFormatException extends PostException{

    private static final String CODE = "F0002";
    private static final String MESSAGE = "The length of comment is not applicable.";

    public CommentFormatException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
