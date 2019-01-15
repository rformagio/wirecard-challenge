package br.com.wirecard.payment.controller.util;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class RestError {

    private String message;
    @Getter private HttpStatus status;
    private List<String> errors;

    public RestError(){}

    public RestError(String message, List<String> errors, HttpStatus status) {
        super();
        this.message = message;
        this.errors = errors;
        this.status = status;
    }

    public RestError(String message, String error, HttpStatus status) {
        super();
        this.message = message;
        errors = Arrays.asList(error);
        this.status = status;
    }
}
