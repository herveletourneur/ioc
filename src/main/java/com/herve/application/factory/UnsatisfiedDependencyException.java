package com.herve.application.factory;

import java.lang.reflect.Type;

public class UnsatisfiedDependencyException extends RuntimeException {

    private UnsatisfiedDependencyException(String message) {
        super(message);
    }

    public static UnsatisfiedDependencyException from(Type aConstructorParameter, Class<?> aClass) {
        return new UnsatisfiedDependencyException("Unable to find a dependency for this type " + aConstructorParameter.getTypeName() + " for this bean type " + aClass + ". Check if dependency is declared in correct layer or if cyclic dependency exist");
    }
}
