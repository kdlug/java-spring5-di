package com.github.di.controllers;

import com.github.di.services.GreetingService;
import com.github.di.services.GreetingServiceImpl;

public class SetterInjectedController {
    // We are using interface
    private GreetingService greetingService;

    String sayHello() {
        return greetingService.sayGreeting();
    }

    // DI
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
