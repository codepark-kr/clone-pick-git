package com.pg.pickgit.exception.authentication;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class AuthenticationException extends ApplicationException {

    protected AuthenticationException(String errorCode, HttpStatus httpStatus, String message){
        super(errorCode, httpStatus, message);
    }
}
