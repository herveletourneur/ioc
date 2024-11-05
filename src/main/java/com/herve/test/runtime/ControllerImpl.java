package com.herve.test.runtime;

import com.herve.application.web.JavalinController;
import io.javalin.http.Context;

import static io.javalin.apibuilder.ApiBuilder.*;

@Controller
public class ControllerImpl implements JavalinController {
    private final HelloWorldService helloWorldService;

    public ControllerImpl(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    @Override
    public void routes() {
        get("/hello", this::hello);
    }

    public void hello(Context context) {
        context.status(200).result(helloWorldService.hello());
    }
}
