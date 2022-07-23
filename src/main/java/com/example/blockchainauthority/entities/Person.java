package com.example.blockchainauthority.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private String name;
    private String emailAddress;
    private String cpf;
    private byte[] publicKeyHash;

    public Person(String name, String emailAddress, String cpf) {
        this.name = name;
        this.emailAddress = emailAddress;
        this.cpf = cpf;
    }
}
