package com.herve.application;

import com.herve.application.configuration.Configuration;
import com.herve.application.configuration.ConfigurationFactory;
import com.herve.application.factory.*;
import com.herve.application.parts.ApplicationParts;
import com.herve.application.web.CustomizeJavalinConfig;
import com.herve.application.web.DefaultJavalinConfigCustomizer;
import com.herve.application.web.WebApplicationBuilder;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultApplicationBuilder implements ApplicationBuilder, WebApplicationBuilder {
    private final Reflections reflections;
    private final ApplicationParts applicationParts;

    public DefaultApplicationBuilder(Reflections reflections, ApplicationParts applicationParts) {
        this.reflections = reflections;
        this.applicationParts = applicationParts;
    }

    @Override
    public CustomizeJavalinConfig buildWith(Configuration configuration) {
        return new DefaultJavalinConfigCustomizer(buildUserSideWith(configuration));
    }

    @Override
    public DependencyLocator buildUserSideWith(Configuration configuration) {
        DependencyTypeReferentialByLayer dependencyTypeReferentialByLayer = loadReferential();
        DependencyHolder configurationDependencyHolder = new ConfigurationFactory().loadConfiguration(configuration);
        DependencyHolder portDependencyHolder = loadPart(applicationParts.clientSideTypeToDetect(), new DependencyHolder().mergeBeanHolder(configurationDependencyHolder), dependencyTypeReferentialByLayer);

        DependencyHolder portAndConfiguration = new DependencyHolder().mergeBeanHolder(portDependencyHolder)
                                                                      .mergeBeanHolder(configurationDependencyHolder);
        DependencyHolder domainDependencyHolder = loadPart(applicationParts.domainTypeToDetect(), portAndConfiguration, dependencyTypeReferentialByLayer);

        DependencyHolder domainAndConfiguration = new DependencyHolder().mergeBeanHolder(domainDependencyHolder)
                                                                        .mergeBeanHolder(configurationDependencyHolder);

        return loadPart(applicationParts.userSideTypeToDetect(), domainAndConfiguration, dependencyTypeReferentialByLayer).mergeBeanHolder(configurationDependencyHolder);
    }

    @Override
    public WebApplicationBuilder web() {
        return this;
    }

    private DependencyHolder loadPart(Set<Class<? extends Annotation>> layers, DependencyHolder configurationDependencyHolder, DependencyTypeReferentialByLayer referential) {
        LayerFactory layerFactory = new LayerFactory(configurationDependencyHolder, new DependencyHolder());
        for (Class<? extends Annotation> layer : layers) {
            layerFactory.createBeans(referential.dependenciesForLayer(layer));
        }
        return layerFactory.currentLayer;
    }

    private DependencyTypeReferentialByLayer loadReferential() {
        Map<Class<?>, Set<Class<?>>> typeToCreateByLayerType  = new HashMap<>();
        for (Class<? extends Annotation> dependencyType : applicationParts.allTypeToDetect()) {
            Set<Class<?>> concreteDependency = reflections.getTypesAnnotatedWith(dependencyType)
                                                          .stream()
                                                          .filter(cl -> !Modifier.isAbstract(cl.getModifiers()))
                                                          .collect(Collectors.toSet());
            typeToCreateByLayerType.put(dependencyType, concreteDependency);
        }
        return new DependencyTypeReferentialByLayer(typeToCreateByLayerType);
    }
}
