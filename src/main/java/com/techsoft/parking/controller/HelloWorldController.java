package com.techsoft.parking.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloWorldController {

    @GetMapping()
    String helloWorld() {
        return "Hello world!";
    }
}
