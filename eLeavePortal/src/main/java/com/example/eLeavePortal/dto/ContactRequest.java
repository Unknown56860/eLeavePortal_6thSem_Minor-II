package com.example.eLeavePortal.dto;

public class ContactRequest {
    private String name;
    private String email;
    private String message;

    // Constructor
    public ContactRequest(){}
    public ContactRequest(String name, String email, String message){
        this.name = name;
        this.email = email;
        this.message = message;
    }

    // Getter
    public String getName(){ return name; }
    public String getEmail(){ return email; }
    public String getMessage(){ return message; }

    // Setter
    public void setName(String name){ this.name = name; }
    public void setEmail(String email){ this.email = email; }
    public void setMessage(String message){ this.message = message; }
}
