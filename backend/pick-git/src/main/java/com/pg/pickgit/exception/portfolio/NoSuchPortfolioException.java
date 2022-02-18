package com.pg.pickgit.exception.portfolio;

import org.springframework.http.HttpStatus;

public class NoSuchPortfolioException extends PortfolioException{

    private static final String ERROR_CODE = "R0001";
    private static final HttpStatus HTTP_STATUS = HttpStatus.BAD_REQUEST;
    private static final String MESSAGE = "Can not inquiry the not exists portfolio.";

    public NoSuchPortfolioException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private NoSuchPortfolioException(
        String errorCode,
        HttpStatus httpStatus,
        String message
    ) {
        super(errorCode, httpStatus, message);
    }
}
