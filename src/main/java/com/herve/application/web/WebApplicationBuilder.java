package com.herve.application.web;

import com.herve.application.configuration.Configuration;
import com.herve.application.factory.DependencyLocator;

public interface WebApplicationBuilder {
    default DependencyLocator buildAndLaunch(Configuration configuration) {
        return buildWith(configuration).customizeJavalinConfig(new DefaultJavalinConfigurer())
                .launchJavalin();
    }

    CustomizeJavalinConfig buildWith(Configuration configuration);
}
