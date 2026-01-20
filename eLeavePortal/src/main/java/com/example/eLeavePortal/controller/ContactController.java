package com.example.eLeavePortal.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.eLeavePortal.dto.ContactRequest;

@RestController
public class ContactController {
    @PostMapping("/contact")
    public String receiveContact(ContactRequest request) {
        System.out.println("Name: " + request.getName());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Message: " + request.getMessage());
        
        return "Message received.";
    }
    
}
