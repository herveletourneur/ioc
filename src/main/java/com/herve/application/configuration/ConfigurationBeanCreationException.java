package com.herve.application.configuration;

public class ConfigurationBeanCreationException extends RuntimeException {
    public ConfigurationBeanCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigurationBeanCreationException(String message) {
        super(message);
    }
}
