package com.example.eLeavePortal.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.eLeavePortal.dto.ContactRequest;

@RestController
public class ContactController {
    @PostMapping("/contact")
    public ResponseEntity<Map<String, String>> receiveContact(
        @RequestBody ContactRequest request) {
            System.out.println("Name: " + request.getName());
            System.out.println("Email: " + request.getEmail());
            System.out.println("Message: " + request.getMessage());

            if(request.getEmail()==null || request.getEmail().isBlank()){
                return ResponseEntity.badRequest().body(
                    Map.of("message", "E-mail is required.")
                );
            }

            try{
                // Save, process, email ...
                
                return ResponseEntity.ok(
                    Map.of("message", "Thank you! Your message has been received.")
                );
            }
            catch (Exception e){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("message", "Server error! Please try later.")
                );
            }
    }
}
