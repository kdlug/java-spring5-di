package com.github.di.controllers;

import org.springframework.stereotype.Controller;

// @Controller indicates that is a spring bean
@Controller
public class MyController {
    public String hello() {
        System.out.println("Hello");

        return "foo";
    }
}
