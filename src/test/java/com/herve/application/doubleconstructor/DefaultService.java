package com.herve.application.doubleconstructor;

import com.herve.application.composition.domain.DomainService;
import com.herve.application.nointerface.IDefaultService;

@DomainService
public class DefaultService implements IDefaultService {
    private DefaultDao defaultDao;

    public DefaultService() {
    }

    public DefaultService(DefaultDao defaultDao) {
        this.defaultDao = defaultDao;
    }
}
