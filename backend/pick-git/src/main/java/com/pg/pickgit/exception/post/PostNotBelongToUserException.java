package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class PostNotBelongToUserException extends PostException{

    private static final String CODE = "P0005";
    private static final String MESSAGE = "The post is not from the user.";

    public PostNotBelongToUserException(){ super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE); }
}
