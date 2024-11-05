package com.herve.application.factory;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class LayerFactoryTest {

    @Test
    public void should_create_beans() {
        //Given
        DependencyHolder dependencyHolder = new DependencyHolder();
        dependencyHolder.register(DataSource.class, new DataSource());
        LayerFactory layerFactory = new LayerFactory(dependencyHolder, new DependencyHolder());
        LayerDefinitions definitions = new LayerDefinitions(Set.of(DefaultDao.class));

        //When
        layerFactory.createBeans(definitions);

        //Then
        Assertions.assertThat(layerFactory.currentLayer.bean(IDefaultDao.class)).isNotNull();
    }

}