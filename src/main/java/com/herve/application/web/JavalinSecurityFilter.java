package com.herve.application.web;

import io.javalin.http.Context;

public interface JavalinSecurityFilter {
    void check(Context context);
}
