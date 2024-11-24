package com.herve.example.domain;

import com.herve.example.rightside.HelloWorlDao;

@Service
public class ServiceImpl implements HelloWorldService {
    private final HelloWorlDao helloWorlDao;

    public ServiceImpl(HelloWorlDao helloWorlDao) {
        this.helloWorlDao = helloWorlDao;
    }

    @Override
    public String hello() {
        return helloWorlDao.retrieveHowToSayHello();
    }
}
