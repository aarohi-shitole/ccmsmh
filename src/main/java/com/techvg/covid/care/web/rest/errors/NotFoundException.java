package com.techvg.covid.care.web.rest.errors;

public class NotFoundException extends RuntimeException{
    private static final long serialVersionUID = -9079454849611061074L;

    public NotFoundException() {
        super();
    }

    public NotFoundException(final String message) {
        super(message);
    }
}
