package com.github.di.services;

public class GreetingServiceImpl implements GreetingService {

    public static final String HELLO = "Hello world";

    @Override
    public String sayGreeting() {
        return HELLO;
    }
}
