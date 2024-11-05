package com.herve.application.configuration;

public class CorrectConfiguration implements Configuration {
    @BeanConfiguration(name = "datasource")
    public DataSource dataSource() {
        return new DataSource();
    }

    @BeanConfiguration(name = "datasource2")
    public DataSource dataSource2() {
        return new DataSource();
    }

    @BeanConfiguration
    public DataSource datasource3() {
        return new DataSource();
    }

}
