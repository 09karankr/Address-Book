package com.bridgelabz.AddressBook.controller;




import com.bridgelabz.AddressBook.dto.AddressBookDTO;
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

    // 1️⃣ GET all contacts
    @GetMapping
    public ResponseEntity<List<AddressBookDTO>> getAllContacts() {
        return ResponseEntity.ok(service.getAllContacts());
    }

    // 2️⃣ GET contact by ID
    @GetMapping("/{id}")
    public ResponseEntity<AddressBookDTO> getContactById(@PathVariable Long id) {
        Optional<AddressBookDTO> dto = service.getContactById(id);
        return dto.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3️⃣ POST - Add a new contact
    @PostMapping
    public ResponseEntity<AddressBookDTO> addContact(@RequestBody AddressBookDTO dto) {
        return ResponseEntity.ok(service.addContact(dto));
    }

    // 4️⃣ PUT - Update an existing contact by ID
    @PutMapping("/{id}")
    public ResponseEntity<AddressBookDTO> updateContact(@PathVariable Long id, @RequestBody AddressBookDTO dto) {
        Optional<AddressBookDTO> updatedDTO = service.updateContact(id, dto);
        return updatedDTO.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 5️⃣ DELETE - Remove a contact by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContact(@PathVariable Long id) {
        if (service.deleteContact(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
