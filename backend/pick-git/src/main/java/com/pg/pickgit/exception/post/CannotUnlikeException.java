package com.pg.pickgit.exception.post;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CannotUnlikeException extends ApplicationException {

    private static final String CODE = "P0004";
    private static final String MESSAGE = "Can not cancel to like to unliked post.";

    public CannotUnlikeException(){ super(CODE, HttpStatus.BAD_REQUEST, MESSAGE); }
}
