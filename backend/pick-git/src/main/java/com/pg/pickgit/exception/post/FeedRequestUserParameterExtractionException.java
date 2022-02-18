package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class FeedRequestUserParameterExtractionException extends PostException{

    private static final String ERROR_CODE = "P0011";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Retrieve parameter for user has been failed.";

    public FeedRequestUserParameterExtractionException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private FeedRequestUserParameterExtractionException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
