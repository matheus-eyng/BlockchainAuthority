package com.example.blockchainauthority;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Getter;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.util.PrivateKeyFactory;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.DefaultDigestAlgorithmIdentifierFinder;
import org.bouncycastle.operator.DefaultSignatureAlgorithmIdentifierFinder;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.operator.bc.BcRSAContentSignerBuilder;
import org.bouncycastle.util.encoders.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Date;

@Component
public class CertificationAuthority {

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private final String PRIVATEKEYB64 = "MIIEpQIBAAKCAQEA7830NJui0HymB3AXoskv4Rcs0IFrjKgY83UN0UCrfe2Bum1J\n" +
            "zDZO5Gu0eBd9QBA6PhV8vUiV5fJl43wztOVVHUn4SbUjfI3cDwOz4MScsbGFNwvv\n" +
            "MFzbldo1usJ9iCcEba4XKaMuUl3w9AzSqNPjEW88bzCWcJ3DetTHMIe/MkmKRm6v\n" +
            "w1NrqNjjGPPru4nd8OM8lmqKZlAKiLHXQzckL/YiA91Qade1hD15OpRqimOihbZt\n" +
            "HbtRdb8k6TgQClxBme/klV2KQNnTqsx0CY1Mm/6yBw7E1NV5M6B9AnFs3otrJTVx\n" +
            "8T3Z5iYNgxFVKVUth4yg8kzLJiFDzToGQ2fTIQIDAQABAoIBAQDGkghxt56ufUkO\n" +
            "R6eKyIW5IfOeBE0Gf/uJPZhfeemHsTAHK5A8ZYwWYYljGDBC6C0SU8CjDIPzkTEF\n" +
            "mkdGt9AvHJ1Yi4IDFYTveeVtbeFC0vi4iE1cAshh4fsz1vET2Rs1pz7O36zvz5JC\n" +
            "xIt7+VjBixbEC7vHLRw9jgwTB5UvEVPlQvGp38NUhIVKRg31yS5z8EmPxODsduiE\n" +
            "Bya0opXs3s3nJo4k33fg9v3j2sNUSYdTfO+nA1TZBTXN2FVHPV3t9/1S5gM5da7c\n" +
            "dAX5+ROnDCpq+ohkUWWWfjux3rrtUWs5YkScjH/Rf2BBAjCak7QgffjFIzBKTijC\n" +
            "bRBYwZdBAoGBAPv433mau1jT64RD48ESSVMoRHgwBdBvw2UEtRpsKNtQX3hPoyZI\n" +
            "QYUI+Gnbb4edZJg/TgVnA+TU+iFtHl9zHKRVmp9Xo4xFMvHa5uK385qYdGaD83Ve\n" +
            "nMLxRvgaKGFcKV3tqBJogyVDsLI2eD42SgqqlWo3oRjMrvz39CKiJBg9AoGBAPOj\n" +
            "SccNW9/wm/Wt5L9LpT8cyISVbFPWBP2Lw1MFnoJ3EWUMWEFJWwfQ7EQ2EAB/m1Yk\n" +
            "zONm/5mhMaGFDZ4Z1J6qmIFThhKXPnUws5vuyUavQHimzY2MGfvUs3CkJ9meXVTt\n" +
            "EWlGuB5oFJ/P12hKmH4J694ML7MdsdBoEVM26HC1AoGBAPnuPEy0rExJuHzzkj/6\n" +
            "UX0InhHRIJNFqLzU1Iam7kfMC0adR6k7VrIM2cq2fIQ6HIPflxZIvlYG4yywvcXT\n" +
            "un9O4hZXqcQDB6tahFYyPJIF0r+09pJfDINZLBoAaDlaXzXp/CJ2RE98OxIqcU7U\n" +
            "LtExrw8yqrqcnJ4TnLeVuNPJAoGBAL1qzgk7kZphbl9retRe83JF5n9tzHLBic+1\n" +
            "wX3ieIHmIFf3aQYZCfThsU622WoJy7MFmWWSxlWixPtWfmfUnUFeUIL7dNkvTRe/\n" +
            "bWRmGYYi6nTi+hx6OBlaHMgOWA0Q2m8UgNzgZnT9Zkt5Q3eJx1T2UbKots0C4SOu\n" +
            "RQn1PnGhAoGAK7P77UbyrjhYzdPNlpm1POO/Sb45GmXXRlKJxLPXO5WxXOULuw9R\n" +
            "QYVYfmJN1cnjD80lFvB+v93T6IC3w0/z8FOWuRLhL4SpTrMdxh8ZAJS0nSCcUfpe\n" +
            "dJ9x9wQ8JMV7QrVC9HR9bxx6eKZRBwkZS2yhN2v3m7TE7Rth96CLM5U=";


    private final String CA_CERT =
            "MIIEPTCCAyWgAwIBAgIUY889uyapvxyR+/o8xkpteSUcwvAwDQYJKoZIhvcNAQEL\n" +
            "BQAwgawxCzAJBgNVBAYTAkJSMRcwFQYDVQQIDA5TYW50YSBDYXRhcmluYTEWMBQG\n" +
            "A1UEBwwNRmxvcmlhbm9wb2xpczENMAsGA1UECgwEVUZTQzEoMCYGA1UECwwfQmxv\n" +
            "Y2tjaGFpbiBhbmQgQ3J5cHRvY3VycmVuY2llczEQMA4GA1UEAwwHVGVzdCBDQTEh\n" +
            "MB8GCSqGSIb3DQEJARYSZW1haWxAcHJvdmlkZXIuY29tMCAXDTIyMDcyMDE2MjIw\n" +
            "NVoYDzMwMjExMTIwMTYyMjA1WjCBrDELMAkGA1UEBhMCQlIxFzAVBgNVBAgMDlNh\n" +
            "bnRhIENhdGFyaW5hMRYwFAYDVQQHDA1GbG9yaWFub3BvbGlzMQ0wCwYDVQQKDARV\n" +
            "RlNDMSgwJgYDVQQLDB9CbG9ja2NoYWluIGFuZCBDcnlwdG9jdXJyZW5jaWVzMRAw\n" +
            "DgYDVQQDDAdUZXN0IENBMSEwHwYJKoZIhvcNAQkBFhJlbWFpbEBwcm92aWRlci5j\n" +
            "b20wggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDvzfQ0m6LQfKYHcBei\n" +
            "yS/hFyzQgWuMqBjzdQ3RQKt97YG6bUnMNk7ka7R4F31AEDo+FXy9SJXl8mXjfDO0\n" +
            "5VUdSfhJtSN8jdwPA7PgxJyxsYU3C+8wXNuV2jW6wn2IJwRtrhcpoy5SXfD0DNKo\n" +
            "0+MRbzxvMJZwncN61Mcwh78ySYpGbq/DU2uo2OMY8+u7id3w4zyWaopmUAqIsddD\n" +
            "NyQv9iID3VBp17WEPXk6lGqKY6KFtm0du1F1vyTpOBAKXEGZ7+SVXYpA2dOqzHQJ\n" +
            "jUyb/rIHDsTU1XkzoH0CcWzei2slNXHxPdnmJg2DEVUpVS2HjKDyTMsmIUPNOgZD\n" +
            "Z9MhAgMBAAGjUzBRMB0GA1UdDgQWBBRyU7a1zOaxGnEGUPFA7MyLd6AVFTAfBgNV\n" +
            "HSMEGDAWgBRyU7a1zOaxGnEGUPFA7MyLd6AVFTAPBgNVHRMBAf8EBTADAQH/MA0G\n" +
            "CSqGSIb3DQEBCwUAA4IBAQBFQojE3vu3c6dCbb9adaUFxXSRIrEV8jtm+hvnCKJv\n" +
            "nIDzmuiac20lTwb209faVfm5F6C7zpmAoEuVzOMnSBvn0c3ON497eVwHD5ToDVTt\n" +
            "7I+EoKS527pVogBxx1gE6DocgjN7ttFmBWE7PVnHfJ1AHBbEKDEVlbk1ZHwmeAtX\n" +
            "tx/5jsKrDDgG+4u1RCotqQay6Bd5GbjPuHjL4H3aSlD3Ixr6yXku7ppG9SKY6evw\n" +
            "1TyBChD+6iAHckm6+bkXVZ55aOeDYRqgsFW+SQRF+jzPDuW6Ubri+5+Ha2iunW0G\n" +
            "OqDnf9UCWEsQXVUhvyWBuH9OQJ3FqiA+VdvvkQuEmLb9";

    private static Logger logger = LoggerFactory.getLogger(CertificationAuthority.class);

    private PrivateKey privateKey;

    @Getter
    private PublicKey publicKey;

    @Getter
    private X509CertificateHolder caCert;

    @Getter
    private ContentSigner contentSigner;


    public CertificationAuthority() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, OperatorCreationException {
        loadKeyPair();
        buildContentSigner();
        loadCertificate();
    }

    private void loadKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.info("LOADING PRIVATE KEY");
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(PRIVATEKEYB64));
        privateKey = factory.generatePrivate(keySpec);

        logger.info("LOADING PUBLIC KEY");
        RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) privateKey;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privKey.getModulus(), privKey.getPublicExponent());
        publicKey = factory.generatePublic(publicKeySpec);
    }

    private void buildContentSigner() throws IOException, OperatorCreationException {
        logger.info("BUILDING CONTENT SIGNER");
        AlgorithmIdentifier signatureAlgorithm = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA256WithRSA");
        AlgorithmIdentifier digestAlgorithm = new DefaultDigestAlgorithmIdentifierFinder().find(signatureAlgorithm);
        BcRSAContentSignerBuilder builder = new BcRSAContentSignerBuilder(signatureAlgorithm, digestAlgorithm);

        AsymmetricKeyParameter asymmetricKeyParameter  = PrivateKeyFactory.createKey(privateKey.getEncoded());
        contentSigner = builder.build(asymmetricKeyParameter);
    }

    private void loadCertificate() throws IOException {
        caCert = new X509CertificateHolder(Base64.decode(CA_CERT));
    }

    public Date sendPreCertificateToContract(X509CertificateHolder certificate) {
        // Send certificate to contract, returns the timestamp response
        return null;
    }
}
