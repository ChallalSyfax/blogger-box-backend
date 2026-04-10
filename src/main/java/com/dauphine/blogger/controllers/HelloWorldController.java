package com.dauphine.blogger.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Hello World API", description = "Hello World endpoints")
public class HelloWorldController {

    @GetMapping("/hello-world")
    @Operation(summary = "Say hello world")
    public String helloWorld() {
        return "Hello World!";
    }

    @GetMapping("/hello")
    @Operation(summary = "Say hello to someone using request param")
    public String hello(@RequestParam(defaultValue = "World") String name) {
        return "Hello " + name + "!";
    }

    @GetMapping("/hello/{name}")
    @Operation(summary = "Say hello to someone using path variable")
    public String helloByName(@PathVariable String name) {
        return "Hello " + name + "!";
    }
}
