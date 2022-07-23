package com.example.blockchainauthority;

import com.example.blockchainauthority.entities.Person;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String issueCertificate(@RequestBody String csr, @RequestParam String cpf) throws Exception {
        X509CertificateHolder certificate = service.issueCertificate(csr, cpf);
        System.out.println(Base64.toBase64String(certificate.getEncoded()));
        return Base64.toBase64String(certificate.getEncoded());
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

    @PostMapping("revoke-certificate")
    public String revokeCertificate(@RequestBody String certificateBase64) throws Exception {
        return service.revokeCertificate(certificateBase64);
    }
}
