package com.herve.application.factory;

public interface DependencyRegistrator {
    <T> void register(Class<T> tClass, Object bean);

    <T>  void register(DependencyKey<T> dependencyKey, Object bean);
}
