package com.example.restfulapiforclearsolutions.DTO;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Builder
@Data
public class UserDTO {

    String firstName;

    String lastName;

    String email;

    LocalDate birthDate;

    String address;

    long phone;

    public UserDTO(String firstName, String lastName, String email, LocalDate birthDate, String address, long phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthDate = birthDate;
        this.address = address;
        this.phone = phone;
    }

    public UserDTO(){}
}
