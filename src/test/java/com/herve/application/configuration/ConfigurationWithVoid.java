package com.herve.application.configuration;

public class ConfigurationWithVoid implements Configuration {
    @BeanConfiguration
    public void dataSource() {
    }
}
