package com.herve.application.factory;

import java.util.List;
import java.util.Optional;

public interface DependencyLocator {
    <T> T bean(Class<T> tClass);
    <T> T bean(DependencyKey<T> dependencyKey);

    <T> Optional<T> tryToFindBean(Class<T> tClass);

    <T> List<T> findAllDependencyOfType(Class<T> dependencyType);
}
