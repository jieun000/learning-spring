package com.myintellij.start.hw;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRestController {
    @RequestMapping("/hi")
    public String hi() {
        return "Hello and World";
    }
    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }
    @RequestMapping("/")
    public String html() {
        return "Spring-Boot <strong>World</strong>";
    }
}