package com.example.blockchainauthority;

import lombok.Getter;
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

@Component
public class CertificationAuthority {

    static {
        Security.insertProviderAt(new BouncyCastleProvider(), 1);
    }

    private String privateKeyB64 = "MIIEpQIBAAKCAQEA7830NJui0HymB3AXoskv4Rcs0IFrjKgY83UN0UCrfe2Bum1J\n" +
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

    private static Logger logger = LoggerFactory.getLogger(CertificationAuthority.class);

    private PrivateKey privateKey;

    @Getter
    private PublicKey publicKey;

    private ContentSigner contentSigner;


    public CertificationAuthority() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, OperatorCreationException {
        loadKeyPair();
        buildContentSigner();
    }

    private void loadKeyPair() throws NoSuchAlgorithmException, InvalidKeySpecException {
        logger.info("LOADING PRIVATE KEY");
        KeyFactory factory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyB64));
        privateKey = factory.generatePrivate(keySpec);

        logger.info("LOADING PUBLIC KEY");
        RSAPrivateCrtKey privKey = (RSAPrivateCrtKey) privateKey;
        RSAPublicKeySpec publicKeySpec = new RSAPublicKeySpec(privKey.getModulus(), privKey.getPublicExponent());
        publicKey = factory.generatePublic(publicKeySpec);
    }

    private void buildContentSigner() throws IOException, OperatorCreationException {
        logger.info("BUILDING CONTENT SIGNER");
        var signatureAlgorithm = new DefaultSignatureAlgorithmIdentifierFinder().find("SHA256WithRSA");
        var digestAlgorithm = new DefaultDigestAlgorithmIdentifierFinder().find(signatureAlgorithm);
        var builder = new BcRSAContentSignerBuilder(signatureAlgorithm, digestAlgorithm);

        var asymmetricKeyParameter  = PrivateKeyFactory.createKey(privateKey.getEncoded());
        contentSigner = builder.build(asymmetricKeyParameter);
    }
}
