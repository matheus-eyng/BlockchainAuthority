package com.example.blockchainauthority;

import com.example.blockchainauthority.entities.Person;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("ca")
public class Controller {

    public ControllerService service;

    public Controller(ControllerService service) {
        this.service = service;
    }

    @PostMapping("issue-certificate")
    public String issueCertificate(@RequestBody String csr) throws IOException {
        service.issueCertificate(csr);
        return null;
    }

    @PostMapping("register-user")
    public String registerPerson(@RequestBody Person person) {
        // GENERATE PERSON KEYPAIR
        try {
            return service.processPersonRegistration(person);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
