package com.herve.application.web;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyLocator;
import io.javalin.config.JavalinConfig;

@FunctionalInterface
public interface JavalinConfigurer {
    void accept(JavalinConfig javalinConfig, DependencyLocator dependencies);
}
