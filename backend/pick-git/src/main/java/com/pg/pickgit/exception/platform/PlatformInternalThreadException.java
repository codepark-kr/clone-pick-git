package com.pg.pickgit.exception.platform;

import org.springframework.http.HttpStatus;

public class PlatformInternalThreadException extends PlatformException{

    private static final String CODE = "V0003";
    private static final String MESSAGE = "Retrieve data from external platform has been failed.";

    public PlatformInternalThreadException(){ this(MESSAGE); }

    private PlatformInternalThreadException(String messeage){ super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE); }
}
