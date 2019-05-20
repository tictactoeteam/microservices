package edu.saddleback.microservices.auth.util;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class TokenUtil {
    private static int EXPIRATION_TIME_MS = 1000 * 60 * 60; // 1 hour
    private static String CERT_PATH = "/opt/cert.pem";

    public static String createToken(String sub) {
        Date issuedAt = new Date();
        Date expiresAt = Date.from(issuedAt.toInstant().plus(EXPIRATION_TIME_MS, ChronoUnit.MILLIS));

        return JWT.create()
                .withSubject(sub)
                .withIssuedAt(issuedAt)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.ECDSA256(null, readKey()));
    }

    private static ECPrivateKey readKey() {
        try {
            PemReader reader = new PemReader(new FileReader(CERT_PATH));
            PemObject object = reader.readPemObject();
            byte[] content = object.getContent();

            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec spec = new PKCS8EncodedKeySpec(content);

            return (ECPrivateKey) kf.generatePrivate(spec);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
