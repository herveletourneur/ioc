package com.herve.application.factory;

import com.herve.application.configuration.IncorrectConfigrationBeanDefinition;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @noinspection unchecked
 */
public class DependencyHolder implements DependencyLocator, DependencyRegistrator {

    private final Map<DependencyKey<?>, Object> beansByBeanKey = new HashMap<>();

    private final Map<Class<?>, Object> beansByType = new HashMap<>();

    @Override
    public <T> T bean(Class<T> dependencyClass) {
        return tryToFindBean(dependencyClass).orElseThrow(() -> new DependencyException("No dependency of type " + dependencyClass + " exist in context."));
    }

    @Override
    public <T> Optional<T> tryToFindBean(Class<T> dependencyClass) {
        T dependency = (T) beansByType.get(dependencyClass);
        if (Objects.isNull(dependency)) {
            var dependenciesFound = beansByBeanKey.keySet()
                                                  .stream()
                                                  .filter(key -> key.dependencyType().equals(dependencyClass))
                                                  .toList();
            if (dependenciesFound.isEmpty()) {
                return Optional.empty();
            } else if (dependenciesFound.size() == 1) {
                return Optional.of((T) beansByBeanKey.get(dependenciesFound.get(0)));
            }
            String dependencies = dependenciesFound.stream()
                                                   .map(DependencyKey::toString)
                                                   .collect(Collectors.joining(",", "[", "]"));
            throw new DependencyException("Multiple dependency of type " + dependencyClass + " exist in context : " + dependencies);
        }
        return Optional.of(dependency);
    }


    @Override
    public <T> T bean(DependencyKey<T> dependencyKey) {
        return Optional.ofNullable((T) beansByBeanKey.get(dependencyKey))
                       .orElseThrow(() -> new IncorrectConfigrationBeanDefinition("No bean existed with key : " + dependencyKey + ". Please check if bean is declared or if name is correct"));

    }


    @Override
    public <T> void register(Class<T> tClass, Object bean) {
        Object oldValue = beansByType.put(tClass, bean);
        if (Objects.nonNull(oldValue)) {
            throw new IncorrectConfigrationBeanDefinition("We try to register a bean by only a type but a dependency is already register. Please configure dependency of same type with diffente name  " + tClass);
        }
    }

    @Override
    public <T> void register(DependencyKey<T> dependencyKey, Object bean) {
        Object oldValue = beansByBeanKey.put(dependencyKey, bean);
        if (Objects.nonNull(oldValue)) {
            throw new IncorrectConfigrationBeanDefinition("We try to register a bean with a key which already reference another bean. bean key : " + dependencyKey);
        }
    }

    public DependencyHolder mergeBeanHolder(DependencyHolder dependencyHolder) {
        dependencyHolder.beansByType.forEach(this::register);
        dependencyHolder.beansByBeanKey.forEach(this::register);
        return this;
    }

    @Override
    public <T> List<T> findAllDependencyOfType(Class<T> dependencyType) {
        List<T> dependencies = (List<T>) beansByBeanKey.entrySet()
                                             .stream()
                                             .filter(entry -> entry.getKey().dependencyType().equals(dependencyType))
                                             .map(Map.Entry::getValue)
                                             .collect(Collectors.toList());
        Optional.ofNullable((T) beansByType.get(dependencyType))
                .ifPresent(dependencies::add);
        return dependencies;
    }
}
