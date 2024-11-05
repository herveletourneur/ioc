package com.herve.test.runtime;

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
