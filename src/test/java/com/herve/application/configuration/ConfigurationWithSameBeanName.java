package com.herve.application.configuration;

public class ConfigurationWithSameBeanName implements Configuration {

    @BeanConfiguration(name = "datasource")
    public DataSource dataSource() {
        return new DataSource();
    }

    @BeanConfiguration(name = "datasource")
    public DataSource dataSource2() {
        return new DataSource();
    }
}
