package com.bridgelabz.AddressBook.service;

import com.bridgelabz.AddressBook.dto.AddressBookDTO;
import com.bridgelabz.AddressBook.model.AddressBookEntry;
import com.bridgelabz.AddressBook.repository.AddressBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service  // Marks this as a Service component
public class AddressBookService {

    @Autowired
    private AddressBookRepository repository;

    // Convert Entity to DTO
    private AddressBookDTO convertToDTO(AddressBookEntry entry) {
        return new AddressBookDTO(entry.getName(), entry.getEmail(), entry.getPhoneNumber());
    }

    // Convert DTO to Entity
    private AddressBookEntry convertToEntity(AddressBookDTO dto) {
        return new AddressBookEntry(null, dto.getName(), dto.getEmail(), dto.getPhoneNumber(), null);
    }

    public List<AddressBookDTO> getAllContacts() {
        return repository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<AddressBookDTO> getContactById(Long id) {
        return repository.findById(id).map(this::convertToDTO);
    }

    public AddressBookDTO addContact(AddressBookDTO dto) {
        AddressBookEntry savedEntry = repository.save(convertToEntity(dto));
        return convertToDTO(savedEntry);
    }

    public Optional<AddressBookDTO> updateContact(Long id, AddressBookDTO updatedDTO) {
        return repository.findById(id).map(existingEntry -> {
            existingEntry.setName(updatedDTO.getName());
            existingEntry.setEmail(updatedDTO.getEmail());
            existingEntry.setPhoneNumber(updatedDTO.getPhoneNumber());
            AddressBookEntry savedEntry = repository.save(existingEntry);
            return convertToDTO(savedEntry);
        });
    }

    public boolean deleteContact(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }
}
