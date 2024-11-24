package com.herve.example;

import com.herve.application.configuration.BeanConfiguration;
import com.herve.application.configuration.Configuration;

public class DefaultConfiguration implements Configuration {
    @BeanConfiguration
    public DataSource dataSource() {
        return new DataSource();
    }
}
