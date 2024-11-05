package com.herve.application;

import com.herve.application.composition.configuration.TestConfiguration;
import com.herve.application.composition.controller.Controller;
import com.herve.application.composition.dao.Dao;
import com.herve.application.composition.domain.DomainService;
import com.herve.application.configuration.Configuration;
import com.herve.application.configuration.ConfigurationBeanCreationException;
import com.herve.application.configuration.CorrectConfiguration;
import com.herve.application.configuration.IncorrectConfigrationBeanDefinition;
import com.herve.application.factory.DependencyException;
import com.herve.application.factory.DependencyLocator;
import com.herve.application.ok.IDefaultController;
import com.herve.application.ok.IDefaultDao;
import com.herve.application.ok.IDefaultService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ApplicationBuilderTest {
    @Test
    public void should_throw_an_excpetion_due_to_same_injection_type_in_different_layer() {
        //Given
        Configuration configuration = new CorrectConfiguration();

        //When
        Assertions.assertThatThrownBy(() ->
                    IoCContainer.scan("com.herve.toto")
                                .clientSideUse(cust -> cust.add(Dao.class))
                                .domainUse(cust -> cust.add(DomainService.class))
                                .userSideUse(cust -> cust.add(Controller.class).add(DomainService.class))
                                .buildUserSideWith(configuration))
                //Then
                .isInstanceOf(ConfigurationBeanCreationException.class);

    }

    @Test
    public void should_throw_an_excpetion_due_to_dependencis_define_in_two_layer() {
        //Given
        Configuration configuration = new TestConfiguration();

        //When
        Assertions.assertThatThrownBy(() ->
                        IoCContainer.scan("com.herve.application.doublelayerdependency")
                                    .clientSideUse(cust -> cust.add(Dao.class))
                                    .domainUse(cust -> cust.add(DomainService.class))
                                    .userSideUse(cust -> cust.add(Controller.class))
                                    .buildUserSideWith(configuration))
                //Then
                .isInstanceOf(IncorrectConfigrationBeanDefinition.class);

    }

    @Test
    public void should_throw_an_excpetion_due_to_dependencis_with_double_constructor() {
        //Given
        Configuration configuration = new TestConfiguration();

        //When
        Assertions.assertThatThrownBy(() ->
                    IoCContainer.scan("com.herve.application.doubleconstructor")
                                .clientSideUse(cust -> cust.add(Dao.class))
                                .domainUse(cust -> cust.add(DomainService.class))
                                .userSideUse(cust -> cust.add(Controller.class))
                                .buildUserSideWith(configuration))
                //Then
                .isInstanceOf(IncorrectConfigrationBeanDefinition.class);

    }

    @Test
    public void should_throw_an_excpetion_with_no_interface_on_one_dependency() {
        //Given
        Configuration configuration = new TestConfiguration();

        //When
        Assertions.assertThatThrownBy(() ->
                    IoCContainer.scan("com.herve.application.nointerface")
                                .clientSideUse(cust -> cust.add(Dao.class))
                                .domainUse(cust -> cust.add(DomainService.class))
                                .userSideUse(cust -> cust.add(Controller.class))
                                .buildUserSideWith(configuration))
                //Then
                .isInstanceOf(IncorrectConfigrationBeanDefinition.class);

    }

    @Test
    public void should_build_application_but_only_user_side_dependency_are_accessible() {
        //Given
        Configuration configuration = new TestConfiguration();

        //When

        DependencyLocator dependencyLocator = IoCContainer.scan("com.herve.application.ok")
                                                          .clientSideUse(cust -> cust.add(Dao.class))
                                                          .domainUse(cust -> cust.add(DomainService.class))
                                                          .userSideUse(cust -> cust.add(Controller.class))
                                                          .buildUserSideWith(configuration);
        //Then
        Assertions.assertThat(dependencyLocator.bean(IDefaultController.class)).isNotNull();
        Assertions.assertThatThrownBy(() -> dependencyLocator.bean(IDefaultDao.class))
                  .isInstanceOf(DependencyException.class);
        Assertions.assertThatThrownBy(() -> dependencyLocator.bean(IDefaultService.class))
                  .isInstanceOf(DependencyException.class);
    }
}
