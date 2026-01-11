package com.example.eLeavePortal.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
public class HomeController {
    
    @GetMapping(path = "/")
    public String home() {
        return "index.html";
    }
    
}
