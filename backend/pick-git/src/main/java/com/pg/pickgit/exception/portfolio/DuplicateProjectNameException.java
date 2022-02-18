package com.pg.pickgit.exception.portfolio;

import org.springframework.http.HttpStatus;

public class DuplicateProjectNameException extends PortfolioException{

    private static final String ERROR_CODE = "R0004";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "The name of project is already exists.";

    public DuplicateProjectNameException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private DuplicateProjectNameException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }

}
