package com.example.appevecommon;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TestEnvVars {
    @Value("${spring.application.secret}")
    private String secret;
    public void printSecret(){
        System.out.println("The secret is: " + secret);
    }
}
