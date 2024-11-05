package com.herve.application.configuration;


import com.herve.application.factory.DependencyHolder;
import com.herve.application.factory.DependencyKey;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class DefaultConfigurationFactoryTest {
    @Test
    public void should_throw_an_exception_if_configuration_contain_void_method_for_conf() {
        //Given
        ConfigurationWithVoid configurationWithVoid = new ConfigurationWithVoid();

        //When
        Assertions.assertThatThrownBy(() -> new ConfigurationFactory().loadConfiguration(configurationWithVoid))
                  //Then
                  .isInstanceOf(IncorrectConfigrationBeanDefinition.class);
    }

    @Test
    public void should_throw_an_exception_if_configuration_contain_thow_configuration_with_same_bean_key() {
        //Given
        ConfigurationWithSameBeanName configuration = new ConfigurationWithSameBeanName();

        //When
        Assertions.assertThatThrownBy(() -> new ConfigurationFactory().loadConfiguration(configuration))
                  //Then
                  .isInstanceOf(ConfigurationBeanCreationException.class);
    }

    @Test
    public void should_create_configuration() {
        //Given
        CorrectConfiguration configuration = new CorrectConfiguration();

        //When
        DependencyHolder dependencyHolder = new ConfigurationFactory().loadConfiguration(configuration);

        //Then
        Assertions.assertThat(dependencyHolder.bean(new DependencyKey<>(DataSource.class, "datasource"))).isNotNull();
        Assertions.assertThat(dependencyHolder.bean(new DependencyKey<>(DataSource.class, "datasource2"))).isNotNull();
        Assertions.assertThat(dependencyHolder.bean(new DependencyKey<>(DataSource.class, "datasource3"))).isNotNull();
    }
}