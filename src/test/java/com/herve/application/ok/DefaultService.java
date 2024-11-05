package com.herve.application.ok;


import com.herve.application.composition.domain.DomainService;

@DomainService
public class DefaultService implements IDefaultService {
    private final IDefaultDao dao;

    public DefaultService(IDefaultDao dao) {
        this.dao = dao;
    }
}
