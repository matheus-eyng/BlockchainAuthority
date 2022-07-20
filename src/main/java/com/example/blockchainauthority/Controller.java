package com.example.blockchainauthority;

import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS10CertificationRequestBuilder;
import org.bouncycastle.pkcs.jcajce.JcaPKCS10CertificationRequestBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("ca")
public class Controller {

    public CertificateService service;

    public Controller(CertificateService service) {
        this.service = service;
    }

    @PostMapping("issue-certificate")
    public String issueCertificate(@RequestBody String csr) throws IOException {
        service.issueCertificate(csr);
        return null;
    }
}
