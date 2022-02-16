package com.pg.pickgit.exception.authentication;

import org.springframework.http.HttpStatus;


public class InvalidTokenException extends AuthenticationException {

    private static final String CODE = "A0001";
    private static final String MESSAGE = "Token Authorization has been failed.";

    public InvalidTokenException(){ super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE); }
}
