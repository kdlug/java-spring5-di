package com.github.di.controllers;

import com.github.di.services.GreetingService;
import org.springframework.stereotype.Controller;

@Controller
public class ConstructorInjectedController {
    // We are using interface
    // We don't have to do autowire here
    // It's automatic if we use constructor based DI
    private GreetingService greetingService;

    // DI
    // @Autowired
    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String sayHello() {
        return greetingService.sayGreeting();
    }
}
