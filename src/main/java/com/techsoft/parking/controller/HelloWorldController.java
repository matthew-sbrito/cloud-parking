package com.techsoft.parking.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/")
@ApiIgnore
public class HelloWorldController {

    @GetMapping("/hello")
    String helloWorld() {
        return "Hello world!";
    }

    @GetMapping("/love")
    String love() {
        return  "I love you girl! Bianca <3 Matheus (com H)";
    }
}
