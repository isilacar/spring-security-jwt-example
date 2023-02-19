package com.isilacar.springsecurityjwtexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping
    public String sayHello(){
        return "Hello";
    }

    @GetMapping("/bye")
    public String sayBye(){
        return "Bye..";
    }
}
