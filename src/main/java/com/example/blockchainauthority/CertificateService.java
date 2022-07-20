package com.example.blockchainauthority;

import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.x509.TBSCertificate;
import org.bouncycastle.asn1.x509.V3TBSCertificateGenerator;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;
import org.bouncycastle.util.io.pem.PemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Calendar;
import java.util.Date;

@Service
public class CertificateService {

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private CertificationAuthority ca;

    @Autowired
    public CertificateService(CertificationAuthority certificationAuthority) {
        this.ca = certificationAuthority;
    }

    public X509CertificateHolder issueCertificate(String pkcs10String) throws IOException {
        PKCS10CertificationRequest csr = convertPemToPKCS10(pkcs10String);

        X509CertificateHolder preCertificate = buildAndSignPreCertificate(csr);

        System.out.println(Base64.toBase64String(preCertificate.getEncoded()));

        return null;
    }

    private PKCS10CertificationRequest convertPemToPKCS10(String pkcs10Pem) {
        try {
            PEMParser pemParser = new PEMParser(new StringReader(pkcs10Pem));
            return (PKCS10CertificationRequest) pemParser.readObject();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BigInteger genSerialNumber() {
        BigInteger randomNumber;
        randomNumber = new BigInteger(256, new SecureRandom("VERY SECURE SEED".getBytes(StandardCharsets.UTF_8)));
        return randomNumber;
    }

    private X509CertificateHolder buildAndSignPreCertificate(PKCS10CertificationRequest csr) throws CertIOException {

        BigInteger serialNumber = genSerialNumber();

        // START DATE -- today
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        Date startDate = calendar.getTime();

        // END DATE -- 1 year from today
        calendar.add(Calendar.YEAR, 1);
        Date endDate = calendar.getTime();


        X509v3CertificateBuilder certBuilder = new X509v3CertificateBuilder(
                ca.getCaCert().getSubject(),
                serialNumber,
                startDate,
                endDate,
                csr.getSubject(),
                csr.getSubjectPublicKeyInfo());

        // POISON EXTENSION -- PRE-CERTIFICATE
        certBuilder.addExtension(new ASN1ObjectIdentifier("1.3.6.1.4.1.11129.2.4.3"), true, DERNull.INSTANCE);

        return certBuilder.build(ca.getContentSigner());
    }
}
