package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class PostNotFoundException extends PostException{

    private static final String ERROR_CODE = "P0002";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "Can not find the post.";

    public PostNotFoundException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private PostNotFoundException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
