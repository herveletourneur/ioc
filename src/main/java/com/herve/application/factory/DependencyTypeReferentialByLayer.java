package com.herve.application.factory;

import com.herve.application.configuration.ConfigurationBeanCreationException;
import com.herve.application.configuration.IncorrectConfigrationBeanDefinition;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public record DependencyTypeReferentialByLayer(Map<Class<?>, Set<Class<?>>> typeToCreateByLayerType) {
    public DependencyTypeReferentialByLayer {
        long sumDependencies = typeToCreateByLayerType.values()
                                                      .stream()
                                                      .mapToLong(Set::size)
                                                      .sum();
        long sumUniqueDependencies = typeToCreateByLayerType.values()
                                                            .stream()
                                                            .flatMap(Set::stream)
                                                            .collect(Collectors.toSet())
                                                            .size();
        if (sumDependencies != sumUniqueDependencies) {
            throw new IncorrectConfigrationBeanDefinition("A dependency is present in two layer please check");
        }
    }

    public LayerDefinitions dependenciesForLayer(Class<?> layer) {
        return new LayerDefinitions(typeToCreateByLayerType.get(layer));
    }
}
