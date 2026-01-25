package com.example.eLeavePortal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.eLeavePortal.dto.ContactDto;
import com.example.eLeavePortal.entity.ContactEntity;
import com.example.eLeavePortal.repository.ContactRepository;

@Service
public class ContactService {
    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    @Transactional
    public void save(ContactDto request){
        ContactEntity contact = new ContactEntity(
            request.getName(),
            request.getEmail(),
            request.getMessage()
        );

        contactRepository.save(contact);
    }
}
