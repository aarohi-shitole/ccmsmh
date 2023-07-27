package com.techvg.covid.care.web.rest.errors;

public class AlreadyExitsException extends RuntimeException{
    private static final long serialVersionUID = 7957651750325704624L;

    public AlreadyExitsException() {
        super();
    }

    public AlreadyExitsException(final String message) {
        super(message);
    }
}
