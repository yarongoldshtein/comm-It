package com.server.commIt.errors;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(DuplicateQuoteException.class)
    public ResponseEntity<?> DuplicateQuoteException() {
        String message = "{\n" +
                "\"errorCode\": 1,\n" +
                " \"description\": \"quote already exist\",\n" +
                " \"level\": \"error\"\n" +
                " }";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalNameException.class)
    public ResponseEntity<?> IllegalNameException() {
        String message = "{\n" +
                "\"errorCode\": 2,\n" +
                " \"description\": \"Invalid name! Name can not be empty.\",\n" +
                " \"level\": \"error\"\n" +
                " }";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalPriceException.class)
    public ResponseEntity<?> IllegalPriceException() {
        String message = "{\n" +
                "\"errorCode\": 3,\n" +
                " \"description\": \"Invalid price! The price can not be a negative number.\",\n" +
                " \"level\": \"error\"\n" +
                " }";
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(QuoteNotFoundException.class)
    public ResponseEntity<?> QuoteNotFoundException(String message) {
        return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}