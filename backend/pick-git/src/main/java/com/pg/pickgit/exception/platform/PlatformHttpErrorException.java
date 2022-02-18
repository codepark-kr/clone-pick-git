package com.pg.pickgit.exception.platform;

import org.springframework.http.HttpStatus;

public class PlatformHttpErrorException extends PlatformException{

    private static final String CODE = "V0001";
    private static final String MESSAGE = "Connect with external platform has been failed.";

    public PlatformHttpErrorException(){ this(MESSAGE); }

    public PlatformHttpErrorException(String message){ super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE); }
}
