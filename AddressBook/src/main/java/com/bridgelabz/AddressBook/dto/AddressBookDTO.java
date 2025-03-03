package com.bridgelabz.AddressBook.dto;



import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBookDTO {
    private String name;
    private String email;
    private String phoneNumber;
    private String address;
}
