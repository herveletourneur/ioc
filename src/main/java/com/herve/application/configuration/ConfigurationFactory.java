package com.herve.application.configuration;

import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyKey;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class ConfigurationFactory {

    public DependencyHolder loadConfiguration(Configuration configuration) {
        Method[] methods = configuration.getClass().getMethods();
        Map<DependencyKey<?>, List<Object>> beans = Arrays.stream(methods)
                                                          .filter(method -> method.isAnnotationPresent(BeanConfiguration.class))
                                                          .filter(method -> method.getGenericParameterTypes().length == 0)
                                                          .collect(Collectors.groupingBy(this::computeBeanKey, Collectors.mapping(method -> createConfiguration(method, configuration), Collectors.toList())));

        List<? extends DependencyKey<?>> duplicateBeans = beans.entrySet()
                                                               .stream()
                                                               .filter(entry -> entry.getValue().size() > 1)
                                                               .map(Map.Entry::getKey)
                                                               .toList();
        if (!duplicateBeans.isEmpty()) {
            String message = duplicateBeans.stream()
                                           .map(DependencyKey::toString)
                                           .collect(Collectors.joining(",", "[", "]"));
            throw new ConfigurationBeanCreationException("Duplicate bean configuration with same type and same name please check configuration " + message);
        }
        return beans.entrySet()
                    .stream()
                    .collect(DependencyHolder::new, (beanHolder, entry) -> beanHolder.register(entry.getKey(), entry.getValue().getFirst()), DependencyHolder::mergeBeanHolder);

    }

    private Object createConfiguration(Method method, Configuration configuration) {
        try {
            return method.invoke(configuration);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new ConfigurationBeanCreationException("Unable to create bean configuration with method name " + method.getName(), e);
        }
    }

    private DependencyKey<?> computeBeanKey(Method method) {
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(Void.TYPE)) {
            throw new IncorrectConfigrationBeanDefinition("Configuration method should return none void type");
        }
        String configurationName = method.getAnnotation(BeanConfiguration.class).name();
        if (Objects.isNull(configurationName) || configurationName.isBlank()) {
            return new DependencyKey<>(returnType, method.getName());
        }
        return new DependencyKey<>(returnType, configurationName);
    }
}
