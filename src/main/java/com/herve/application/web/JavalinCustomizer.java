package com.herve.application.web;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyLocator;
import io.javalin.Javalin;

public interface JavalinCustomizer {
    void customize(Javalin javalin, DependencyLocator dependencyLocator);
}
