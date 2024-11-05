package com.herve.application;

import com.herve.application.configuration.Configuration;
import com.herve.application.factory.DependencyLocator;
import com.herve.application.web.WebApplicationBuilder;

public interface ApplicationBuilder {
    DependencyLocator buildUserSideWith(Configuration configuration);

    WebApplicationBuilder web();
}
