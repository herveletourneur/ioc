package com.herve.application.web;

import com.herve.application.factory.DependencyLocator;
import io.javalin.Javalin;

import java.util.Comparator;

public class DefaultJavalinCustomizer implements JavalinCustomizer {
    @Override
    public void customize(Javalin javalin, DependencyLocator dependencyLocator) {
        dependencyLocator.findAllDependencyOfType(JavalinFilter.class)
                        .stream()
                        .sorted(Comparator.comparingInt(JavalinFilter::order))
                        .forEach(filter -> {
                            if (filter instanceof JavalinPathFilter jpf) {
                                javalin.before(jpf.path(), filter::before);
                                javalin.after(jpf.path(), filter::after);
                            } else {
                                javalin.before(filter::before);
                                javalin.after(filter::after);
                            }
                        });
        dependencyLocator.tryToFindBean(JavalinSecurityFilter.class)
                        .ifPresent(filter -> javalin.beforeMatched(filter::check));
    }
}
