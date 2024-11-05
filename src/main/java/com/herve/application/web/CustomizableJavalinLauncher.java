package com.herve.application.web;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyLocator;
import io.javalin.Javalin;

public class CustomizableJavalinLauncher implements JavalinLauncher {
    private final DependencyLocator dependencies;

    private final Javalin javalin;

    public CustomizableJavalinLauncher(DependencyLocator dependencies, Javalin javalin) {
        this.dependencies = dependencies;
        this.javalin = javalin;
    }

    @Override
    public DependencyLocator launchJavalin(JavalinCustomizer javalinCustomizer, int port) {
        javalinCustomizer.customize(javalin, dependencies);
        javalin.start(port);
        return dependencies;
    }
}
