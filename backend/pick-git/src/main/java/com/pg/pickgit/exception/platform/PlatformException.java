package com.pg.pickgit.exception.platform;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class PlatformException extends ApplicationException {

    protected PlatformException(String errorCode, HttpStatus httpStatus, String message){
        super(errorCode, httpStatus, message);
    }
}
