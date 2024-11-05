package com.herve.application.web;


import io.javalin.http.Context;

public interface JavalinFilter {
    int order();

    default void before(Context context) {}

    default void after(Context context) {}
}
