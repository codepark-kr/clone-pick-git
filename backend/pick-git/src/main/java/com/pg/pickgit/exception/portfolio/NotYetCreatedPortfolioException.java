package com.pg.pickgit.exception.portfolio;

import org.springframework.http.HttpStatus;

public class NotYetCreatedPortfolioException extends PortfolioException{

    private static final String ERROR_CODE = "R0006";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NO_CONTENT;
    private static final String MESSAGE = "The portfolio doesn't generated yet.";

    public NotYetCreatedPortfolioException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private NotYetCreatedPortfolioException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }

}
