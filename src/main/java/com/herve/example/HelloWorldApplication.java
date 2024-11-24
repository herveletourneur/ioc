package com.herve.example;

import com.herve.application.IoCContainer;
import com.herve.example.domain.Service;
import com.herve.example.rightside.Dao;
import com.herve.example.rightside.Repository;
import com.herve.example.leftside.Controller;
import com.herve.example.leftside.Filter;
import com.herve.example.leftside.SecurityFilter;

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
