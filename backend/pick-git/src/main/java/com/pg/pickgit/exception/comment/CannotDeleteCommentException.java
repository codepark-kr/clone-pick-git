package com.pg.pickgit.exception.comment;

import org.springframework.http.HttpStatus;

public class CannotDeleteCommentException extends CommentException{

    private static final String CODE = "P0007";
    private static final String MESSAGE = "Can not delete other's comment.";

    protected CannotDeleteCommentException() {
        super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE);
    }
}
