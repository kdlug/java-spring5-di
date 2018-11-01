package com.github.di.controllers;

import com.github.di.services.GreetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;

@Controller
public class SetterInjectedController {
    // We are using interface
    private GreetingService greetingService;

    public String sayHello() {
        return greetingService.sayGreeting();
    }

    // DI
    @Autowired
    @Qualifier("setterGreetingService")
    public void setGreetingService(GreetingService greetingService) {
        this.greetingService = greetingService;
    }
}
