package com.herve.application.web;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyLocator;
import io.javalin.config.JavalinConfig;

import java.util.List;

public class DefaultJavalinConfigurer implements JavalinConfigurer {

    @Override
    public void accept(JavalinConfig javalinConfig, DependencyLocator dependencies) {
        List<JavalinController> controllers = dependencies.findAllDependencyOfType(JavalinController.class);
        javalinConfig.router.apiBuilder(() -> controllers.forEach(JavalinController::routes));
        javalinConfig.useVirtualThreads = true;
    }
}
