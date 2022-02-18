package com.pg.pickgit.exception.post;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class NoSuchTagException extends ApplicationException {

    private static final String ERROR_CODE = "P0009";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "The tag is not exists.";

    public NoSuchTagException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private NoSuchTagException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
