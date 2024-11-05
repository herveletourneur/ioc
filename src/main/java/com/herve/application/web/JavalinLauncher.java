package com.herve.application.web;

import com.herve.application.factory.DependencyLocator;

public interface JavalinLauncher {
    int defaultPort = 8080;

    DependencyLocator launchJavalin(JavalinCustomizer javalinCustomizer, int port);

    default DependencyLocator launchJavalin(JavalinCustomizer javalinCustomizer) {
        return launchJavalin(javalinCustomizer, defaultPort);
    }

    default DependencyLocator launchJavalin(int port) {
        return launchJavalin(new DefaultJavalinCustomizer(), port);
    }

    default DependencyLocator launchJavalin() {
        return launchJavalin(defaultPort);
    }

}
