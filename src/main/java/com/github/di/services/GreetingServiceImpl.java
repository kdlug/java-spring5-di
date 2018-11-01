package com.github.di.services;

import org.springframework.stereotype.Service;

@Service
public class GreetingServiceImpl implements GreetingService {

    public static final String HELLO = "Hello world";

    @Override
    public String sayGreeting() {
        return HELLO;
    }
}
