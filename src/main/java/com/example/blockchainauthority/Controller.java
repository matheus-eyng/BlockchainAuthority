package com.example.blockchainauthority;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("ca")
public class Controller {

    public CertificateService service;

    public Controller(CertificateService service) {
        this.service = service;
    }
}
