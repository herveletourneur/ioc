package com.herve.test.runtime;

import com.herve.application.IoCContainer;

public class HelloWorldApplication {
    public static void main(String[] args) {
        IoCContainer.scan("com.herve.example")
                    .clientSideUse(clientAnnotations -> clientAnnotations.add(Dao.class)
                                                                         .add(Repository.class))
                    .domainUse(domainAnnotations -> domainAnnotations.add(Service.class))
                    .userSideUse(userAnnotations -> userAnnotations.add(Controller.class)
                                                                   .add(Filter.class)
                                                                   .add(SecurityFilter.class))
                    .web()
                    .buildAndLaunch(new DefaultConfiguration());
    }
}
