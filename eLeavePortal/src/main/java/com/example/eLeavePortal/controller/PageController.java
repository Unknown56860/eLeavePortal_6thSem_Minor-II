package com.example.eLeavePortal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    
    @GetMapping(path = "/")
    public String home() { return "index"; }

    @GetMapping("/register")
    public String registerPage() { return "auth/register"; }

    @GetMapping("/login")
    public String loginPage() { return "auth/login"; }
}
