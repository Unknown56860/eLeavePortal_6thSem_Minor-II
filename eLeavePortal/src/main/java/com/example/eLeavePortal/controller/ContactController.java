package com.example.eLeavePortal.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.http.ResponseEntity;

import com.example.eLeavePortal.dto.ContactDto;
import com.example.eLeavePortal.service.ContactService;

@RestController
@RequestMapping("/contact")
public class ContactController {
    private final ContactService contactService;

    public ContactController(ContactService contactService){
        this.contactService = contactService;
        
        System.out.println("Contact ctrl init");
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> receiveContact(
            @Valid @RequestBody ContactDto request) {

        contactService.save(request);

        return ResponseEntity.ok(
            Map.of("message", "Thank you! Your message has been received.")
        );
    }
    
}
