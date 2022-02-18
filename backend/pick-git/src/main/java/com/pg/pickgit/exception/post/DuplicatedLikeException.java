package com.pg.pickgit.exception.post;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class DuplicatedLikeException extends ApplicationException {

    private static final String CODE = "P0003";
    private static final String MESSAGE = "Can not like to already liked post.";

    public DuplicatedLikeException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
