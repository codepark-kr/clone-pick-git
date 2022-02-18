package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class IllegalSearchTypeException extends PostException{

    private static final String ERROR_CODE = "P0006";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "The type of search does not exists.";

    public IllegalSearchTypeException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private IllegalSearchTypeException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }

}
