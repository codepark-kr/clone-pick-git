package com.pg.pickgit.exception.comment;

import org.springframework.http.HttpStatus;

public class CommentNotFoundException extends CommentException{

    private static final String CODE = "P0008";
    private static final String MESSAGE = "The comment doesn't exists.";

    public CommentNotFoundException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }

}
