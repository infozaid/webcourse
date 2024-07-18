package com.infozaid.demo.customer;

public record CustomerRegistrationRequest(String name,
                                           String email,
                                           Integer age) {
}
