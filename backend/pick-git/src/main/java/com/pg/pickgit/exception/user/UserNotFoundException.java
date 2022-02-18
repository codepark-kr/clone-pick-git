package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends UserException{

    private static final String ERROR_CODE = "U0001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "Can not find the user.";

    public UserNotFoundException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    public UserNotFoundException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
