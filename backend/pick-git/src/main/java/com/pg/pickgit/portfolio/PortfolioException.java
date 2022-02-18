package com.pg.pickgit.portfolio;

import com.pg.pickgit.exception.ApplicationException;
import org.springframework.http.HttpStatus;

public abstract class PortfolioException extends ApplicationException {

    protected PortfolioException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
