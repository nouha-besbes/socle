package com.socle.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.METHOD_NOT_ALLOWED)
public class MethodNotAllowedException extends Exception {

    private static final long serialVersionUID = 1L;

    public MethodNotAllowedException(String message) {
        super(message);
    }
}
