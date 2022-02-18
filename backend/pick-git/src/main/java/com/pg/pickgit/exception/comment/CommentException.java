package com.pg.pickgit.exception.comment;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public class CommentException extends ApplicationException {
    protected CommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
