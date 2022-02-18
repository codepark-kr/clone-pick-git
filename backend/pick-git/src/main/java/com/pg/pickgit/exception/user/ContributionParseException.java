package com.pg.pickgit.exception.user;

import org.springframework.http.HttpStatus;

public class ContributionParseException extends UserException{

    private static final String CODE = "V0001";
    private static final String MESSAGE = "Can not load activity statics.";

    public ContributionParseException(){ super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE); }
}
