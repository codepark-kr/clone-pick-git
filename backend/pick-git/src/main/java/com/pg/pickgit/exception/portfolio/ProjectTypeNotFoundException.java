package com.pg.pickgit.exception.portfolio;

import org.springframework.http.HttpStatus;

public class ProjectTypeNotFoundException extends PortfolioException{

    private static final String ERROR_CODE = "R0002";
    private static final HttpStatus HTTP_STATUS = HttpStatus.NOT_FOUND;
    private static final String MESSAGE = "Can not find the project.";

    public ProjectTypeNotFoundException(){ this(ERROR_CODE, HTTP_STATUS, MESSAGE); }

    private ProjectTypeNotFoundException(
            String errorCode,
            HttpStatus httpStatus,
            String message
    ){
        super(errorCode, httpStatus, message);
    }
}
