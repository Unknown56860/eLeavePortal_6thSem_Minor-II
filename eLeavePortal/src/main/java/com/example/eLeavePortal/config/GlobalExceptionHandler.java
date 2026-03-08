package com.example.eLeavePortal.config;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(
            MethodArgumentNotValidException ex){
        
        String message = ex.getBindingResult().getFieldError().getDefaultMessage();

        return ResponseEntity.badRequest()
            .body(Map.of("message", message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneral(Exception e){

        String message = e.getMessage();

        return ResponseEntity.internalServerError()
            .body(Map.of("message", "Server error: " + message));
    }
}
