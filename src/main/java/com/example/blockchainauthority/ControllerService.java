package com.example.blockchainauthority;

import com.example.blockchainauthority.contract.BlockchainService;
import com.example.blockchainauthority.contract.PersonRegistry;
import com.example.blockchainauthority.entities.Person;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.cert.CertIOException;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.X509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;

@Service
public class ControllerService {

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private static final Logger log = LoggerFactory.getLogger(ControllerService.class);

    private CertificationAuthority ca;
    private BlockchainService blockchainService;

    @Autowired
    public ControllerService(CertificationAuthority certificationAuthority, BlockchainService blockchainService) {
        this.ca = certificationAuthority;
        this.blockchainService = blockchainService;
    }

    public X509CertificateHolder issueCertificate(String pkcs10String, String cpf) throws Exception {
        MessageDigest hasher = MessageDigest.getInstance("SHA-256");

        if (!blockchainService.checkIfRegistered(cpf)) {
            throw new RuntimeException("User not registered!");
        }


        PKCS10CertificationRequest csr = convertPemToPKCS10(pkcs10String);


        X509CertificateHolder preCertificate = buildAndSignPreCertificate(csr);

        PersonRegistry.Person person = blockchainService.getPersonFromRegistry(cpf);

        byte[] timestamp = blockchainService.loadPreCertificateIntoLog(hasher.digest(preCertificate.getEncoded()), person);
        // Assim que deve ser feito o hash byte32 do certificado
//        byte[] bytes = MessageDigest.getInstance("SHA-256").digest(preCertificate.getEncoded());
        /*
        Mandar pre certificado pro log
        Pegar o resultado (timestamp)
        Emitir outro certificado e adcionar a timestamp (em algum lugar)
        retornar esse certificado pra pessoa

        fazer revogacao...
         */

        log.info("Issuing final certificate...");
        return buildAndSignCertificate(csr, timestamp);
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

    private X509CertificateHolder buildAndSignCertificate(PKCS10CertificationRequest csr, byte[] timestamp) throws CertIOException {

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
        certBuilder.addExtension(new ASN1ObjectIdentifier("1.2.3.4.5.6.7.8"), false, timestamp);

        return certBuilder.build(ca.getContentSigner());
    }

    public String processPersonRegistration(Person person) throws Exception {
        log.info("Generating " + person.getName() + "'s keypair");
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        PublicKey publicKey = keyPair.getPublic();

        // Hashes the public key
        person.setPublicKeyHash(MessageDigest.getInstance("SHA-256").digest(publicKey.getEncoded()));

        return blockchainService.addPersonToRegistry(person);
    }
}
