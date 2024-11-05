package com.herve.application.factory;

public record DependencyKey<T>(Class<T> dependencyType, String name) {
}
