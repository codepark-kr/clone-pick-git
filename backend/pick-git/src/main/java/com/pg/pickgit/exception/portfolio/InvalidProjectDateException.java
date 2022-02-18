package com.pg.pickgit.exception.portfolio;

import org.springframework.http.HttpStatus;

public class InvalidProjectDateException extends PortfolioException{

    private static final String ERROR_CODE = "R0003";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "The start date of this project is after than end date.";

    public InvalidProjectDateException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private InvalidProjectDateException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
