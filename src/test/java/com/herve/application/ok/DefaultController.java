package com.herve.application.ok;

import com.herve.application.composition.controller.Controller;

@Controller
public class DefaultController implements IDefaultController {
    private final IDefaultService domainService;

    public DefaultController(IDefaultService domainService) {
        this.domainService = domainService;
    }
}
