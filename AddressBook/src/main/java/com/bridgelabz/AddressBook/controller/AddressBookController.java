package com.bridgelabz.AddressBook.controller;



import com.bridgelabz.AddressBook.model.AddressBookEntry;
import com.bridgelabz.AddressBook.repository.AddressBookRepository;
//import com.bridgelabz.AddressBook.model.AddressBookEntry;
//import com.bridgelabz.AddressBook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/addressbook")
public class AddressBookController {

    @Autowired
    private AddressBookRepository repository;

    // 1️⃣ GET all contacts
    @GetMapping
    public ResponseEntity<List<AddressBookEntry>> getAllContacts() {
        return ResponseEntity.ok(repository.findAll());
    }

    // 2️⃣ GET contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressBookEntry> getContactById(@PathVariable Long id) {
        Optional<AddressBookEntry> entry = repository.findById(id);
        return entry.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3️⃣ POST - Add a new contact
    @PostMapping
    public ResponseEntity<AddressBookEntry> addContact(@RequestBody AddressBookEntry entry) {
        AddressBookEntry savedEntry = repository.save(entry);
        return ResponseEntity.ok(savedEntry);
    }

    // 4️⃣ PUT - Update an existing contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<AddressBookEntry> updateContact(@PathVariable Long id, @RequestBody AddressBookEntry updatedEntry) {
        return repository.findById(id)
                .map(existingEntry -> {
                    updatedEntry.setId(id);
                    AddressBookEntry savedEntry = repository.save(updatedEntry);
                    return ResponseEntity.ok(savedEntry);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // 5️⃣ DELETE - Remove a contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}