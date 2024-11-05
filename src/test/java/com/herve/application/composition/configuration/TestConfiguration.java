package com.herve.application.composition.configuration;

import com.herve.application.configuration.BeanConfiguration;
import com.herve.application.configuration.Configuration;

public class TestConfiguration implements Configuration {

    @BeanConfiguration
    public DataSource dataSource() {
        return new DataSource();
    }
}
