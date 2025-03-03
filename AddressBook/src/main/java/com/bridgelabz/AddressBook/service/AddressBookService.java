package com.bridgelabz.AddressBook.service;


import com.bridgelabz.AddressBook.dto.AddressBookDTO;
import com.bridgelabz.AddressBook.model.AddressBookEntry;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AddressBookService {

    private final List<AddressBookEntry> addressBook = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong(1);  // Unique ID generator

    // Convert DTO to Entity
    private AddressBookEntry convertToEntity(AddressBookDTO dto) {
        return new AddressBookEntry(counter.getAndIncrement(), dto.getName(), dto.getEmail(), dto.getPhoneNumber(), dto.getAddress());
    }

    // Convert Entity to DTO
    private AddressBookDTO convertToDTO(AddressBookEntry entry) {
        return new AddressBookDTO(entry.getName(), entry.getEmail(), entry.getPhoneNumber(), entry.getAddress());
    }

    // GET all contacts
    public List<AddressBookDTO> getAllContacts() {
        return addressBook.stream().map(this::convertToDTO).toList();
    }

    // GET contact by ID
    public Optional<AddressBookDTO> getContactById(Long id) {
        return addressBook.stream()
                .filter(entry -> entry.getId().equals(id))
                .map(this::convertToDTO)
                .findFirst();
    }

    // POST - Add a new contact
    public AddressBookDTO addContact(AddressBookDTO dto) {
        AddressBookEntry newEntry = convertToEntity(dto);
        addressBook.add(newEntry);
        return convertToDTO(newEntry);
    }

    // PUT - Update a contact by ID
    public Optional<AddressBookDTO> updateContact(Long id, AddressBookDTO updatedDTO) {
        return addressBook.stream()
                .filter(entry -> entry.getId().equals(id))
                .findFirst()
                .map(existingEntry -> {
                    existingEntry.setName(updatedDTO.getName());
                    existingEntry.setEmail(updatedDTO.getEmail());
                    existingEntry.setPhoneNumber(updatedDTO.getPhoneNumber());
                    existingEntry.setAddress(updatedDTO.getAddress());
                    return convertToDTO(existingEntry);
                });
    }

    // DELETE - Remove a contact by ID
    public boolean deleteContact(Long id) {
        return addressBook.removeIf(entry -> entry.getId().equals(id));
    }
}
