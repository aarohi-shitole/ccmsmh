package com.techvg.covid.care.web.rest.errors;

public class CustomMessageException extends RuntimeException{
    private static final long serialVersionUID = 7957651750325704624L;

    public CustomMessageException() {
        super();
    }

    public CustomMessageException(final String message) {
        super(message);
    }
}
