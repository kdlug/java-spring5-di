package com.github.di.controllers;

import com.github.di.services.GreetingService;
import org.springframework.stereotype.Controller;

// @Controller indicates that is a spring bean
@Controller
public class MyController {
    private GreetingService greetingService;

    public MyController(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public String hello() {
        return greetingService.sayGreeting();
    }
}
