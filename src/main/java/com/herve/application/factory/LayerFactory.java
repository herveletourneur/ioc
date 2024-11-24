package com.herve.application.factory;

import com.herve.application.configuration.IncorrectConfigrationBeanDefinition;

import javax.inject.Named;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class LayerFactory {

    public final DependencyHolder layerBeanHolder;

    public final DependencyHolder currentLayer;


    public LayerFactory(DependencyHolder layerBeanHolder, DependencyHolder currentSubLayer) {
        this.layerBeanHolder = layerBeanHolder;
        this.currentLayer = currentSubLayer;
    }

    public void createBeans(LayerDefinitions layerDefinitions) {
        layerDefinitions.definitions().forEach(this::createAndRegisterBean);
    }

    private void createAndRegisterBean(Class<?> dependency) {
        Constructor<?>[] constructors = dependency.getConstructors();
        if (constructors.length != 1) {
            throw new IncorrectConfigrationBeanDefinition("A dependency must have only one constructor : " + dependency.getName());
        }
        Constructor<?> injectionPoint = constructors[0];
        int parameterCount = 0;
        Annotation[][] parameterAnnotations = injectionPoint.getParameterAnnotations();

        List<Object> parameterToInject = new ArrayList<>();
        for (Type aConstructorParameter : injectionPoint.getGenericParameterTypes()) {

            Object dependencyToInject = Arrays.stream(parameterAnnotations[parameterCount])
                                              .filter(annotation -> annotation.annotationType() == Named.class)
                                              .map(Named.class::cast)
                                              .map(Named::value)
                                              .findFirst()
                                              .map(dependencyName -> new DependencyKey<>((Class<?>) aConstructorParameter, dependencyName))
                                              .map(key -> (Object) layerBeanHolder.bean(key))
                                              .or(() -> Optional.ofNullable(layerBeanHolder.bean((Class<?>) aConstructorParameter)))
                                              .orElseThrow(() -> UnsatisfiedDependencyException.from(aConstructorParameter, dependency));
            parameterToInject.add(dependencyToInject);
            parameterCount++;
        }
        try {
            Object dependencyInstance = injectionPoint.newInstance(parameterToInject.toArray());
            if (dependency.getInterfaces().length == 0) {
                throw new IncorrectConfigrationBeanDefinition("No interface found for : " + dependency.getName() + " only interfaces are referenced in container");
            }
            Optional.ofNullable(dependency.getAnnotation(Named.class))
                    .map(Named::value)
                    .ifPresentOrElse(
                            name -> registerByKey(dependency, dependencyInstance, name),
                            () -> registerByType(dependency, dependencyInstance));

        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new BeanCreationException("Unable to create bean of type " + dependency, e);
        }
    }

    private void registerByType(Class<?> dependencyType, Object dependencyInstance) {
        interfaceForType(dependencyType).forEach(key -> {
                                            layerBeanHolder.register(key, dependencyInstance);
                                            currentLayer.register(key, dependencyInstance);
                                        });
    }

    private void registerByKey(Class<?> dependencyType, Object dependencyInstance, String dependencyName) {
        interfaceForType(dependencyType).map(interf -> new DependencyKey<>(interf, dependencyName))
                                        .forEach(key -> {
                                            layerBeanHolder.register(key, dependencyInstance);
                                            currentLayer.register(key, dependencyInstance);
                                        });
    }

    private Stream<Class<?>> interfaceForType(Class<?> type) {
        return Stream.of(type.getInterfaces())
                     .flatMap(interfaceType -> Stream.concat(Stream.of(interfaceType), interfaceForType(interfaceType)));
    }
}
