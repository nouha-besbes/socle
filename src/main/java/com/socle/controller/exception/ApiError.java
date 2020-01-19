package com.socle.controller.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiError {

    private Date timestamp;
    private HttpStatus status;
    private List<String> errors;
    private String message;

}
