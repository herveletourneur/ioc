package com.herve.application.factory;

public class BeanCreationException extends RuntimeException {

    public BeanCreationException(String message, Throwable e) {
        super(message, e);
    }
}
