package com.github.di.controllers;

import com.github.di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SetterInjectedController {
    // We are using interface
    @Autowired
    private GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }

    // DI
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
