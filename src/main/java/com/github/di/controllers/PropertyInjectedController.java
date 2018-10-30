package com.github.di.controllers;

import com.github.di.services.GreetingServiceImpl;

public class PropertyInjectedController {
    // Dependency injection in property
    public GreetingServiceImpl greetingService;

    String sayHello() {
        return greetingService.sayGreeting();
    }
}
