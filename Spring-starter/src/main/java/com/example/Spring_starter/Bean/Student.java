package com.example.Spring_starter.Bean;

import org.springframework.stereotype.Component;

@Component
public class Student {

    public Student(){
        System.out.println("Hello Student bean got initilized");
    }
}
