package com.bridgelabz.AddressBook.controller;



import com.bridgelabz.AddressBook.model.AddressBookEntry;
import com.bridgelabz.AddressBook.service.AddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {
    @Autowired
    private AddressBookService service;

    @GetMapping
    public List<AddressBookEntry> getAllContacts() {
        return service.getAllContacts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressBookEntry> getContactById(@PathVariable Long id) {
        return service.getContactById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AddressBookEntry addContact(@RequestBody AddressBookEntry entry) {
        return service.saveContact(entry);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressBookEntry> updateContact(@PathVariable Long id, @RequestBody AddressBookEntry updatedEntry) {
        return service.getContactById(id)
                .map(existingEntry -> {
                    updatedEntry.setId(id);
                    return ResponseEntity.ok(service.saveContact(updatedEntry));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        service.deleteContact(id);
        return ResponseEntity.noContent().build();
    }
}
