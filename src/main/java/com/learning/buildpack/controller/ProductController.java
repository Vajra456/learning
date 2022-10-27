package com.learning.buildpack.controller;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @GetMapping("/")
    public String index() {
        return "{ 'message' : 'Jai Jagannath'}";
    }
}
