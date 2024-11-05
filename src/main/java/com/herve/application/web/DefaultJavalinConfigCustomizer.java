package com.herve.application.web;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyLocator;
import io.javalin.Javalin;

public class DefaultJavalinConfigCustomizer implements CustomizeJavalinConfig {
    private final DependencyLocator dependencyHolder;

    public DefaultJavalinConfigCustomizer(DependencyLocator dependencyHolder) {
        this.dependencyHolder = dependencyHolder;
    }

    @Override
    public JavalinLauncher customizeJavalinConfig(JavalinConfigurer configurer) {
        Javalin app = Javalin.create(config -> configurer.accept(config, dependencyHolder));
        return new CustomizableJavalinLauncher(dependencyHolder, app);
    }
}
