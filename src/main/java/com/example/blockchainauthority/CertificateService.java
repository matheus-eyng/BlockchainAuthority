package com.example.blockchainauthority;

import org.bouncycastle.cert.X509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateService {

    private CertificationAuthority certificationAuthority;

    @Autowired
    public CertificateService(CertificationAuthority certificationAuthority) {
        this.certificationAuthority = certificationAuthority;
    }

    public X509CertificateHolder issueCertificate() {
        //
        return null;
    }
}
