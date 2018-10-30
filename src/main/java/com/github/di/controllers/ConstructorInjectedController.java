package com.github.di.controllers;

import com.github.di.services.GreetingService;

public class ConstructorInjectedController {
    // We are using interface
    private GreetingService greetingService;

    // DI
    public ConstructorInjectedController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    String sayHello() {
        return greetingService.sayGreeting();
    }
}
