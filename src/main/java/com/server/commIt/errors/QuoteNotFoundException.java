package com.server.commIt.errors;

public class QuoteNotFoundException extends Exception{
    public QuoteNotFoundException(String message) {
        super(message);
    }
}
