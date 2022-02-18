package com.pg.pickgit.exception.post;

import org.springframework.http.HttpStatus;

public class RepositoryParseException extends PostException{

    private static final String CODE = "V0001";
    private static final String MESSAGE = "Can not load the list of repositories.";

    public RepositoryParseException(){ super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE); }
}
