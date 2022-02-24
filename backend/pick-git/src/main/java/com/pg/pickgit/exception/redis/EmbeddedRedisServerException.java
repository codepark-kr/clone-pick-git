package com.pg.pickgit.exception.redis;

import org.springframework.http.HttpStatus;
import com.pg.pickgit.exception.ApplicationException;

public class EmbeddedRedisServerException extends ApplicationException {

    private static final String ERROR_CODE = "V0002";
    private static final HttpStatus HTTP_STATUS = HttpStatus.INTERNAL_SERVER_ERROR;
    private static final String MESSAGE = "Connect to internal server of Redis has been failed.";

    public EmbeddedRedisServerException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private EmbeddedRedisServerException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
