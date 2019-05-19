package edu.saddleback.microservices.cart.util;

import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.interfaces.ECPublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemReader;

public class TokenUtil {
    private static final String PUB_PATH = "/opt/pub.pem";

    /**
     * Verifies a JWT and returns the subject
     * @param authHeader `Authorization` header specified in the HTTP request
     * @return the subject specified in the JWT
     * @throws ErrorResponse if the token is invalid or expired
     */
    public static String verifyToken(String authHeader) throws ErrorResponse {
        if (authHeader == null || authHeader.isEmpty()) {
            throw new ErrorResponse(401, "Unauthenticated, please log in", "UNAUTHENTICATED");
        }
        if (!authHeader.startsWith("Bearer ")) {
            throw new ErrorResponse(403, "Invalid bearer token, this route requires auth", "INVALID_TOKEN");
        }

        String token = authHeader.substring(7);

        Algorithm algorithm = Algorithm.ECDSA256(readKey(), null);
        JWTVerifier verifier = JWT.require(algorithm).build();

        DecodedJWT jwt;
        try {
            jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            throw new ErrorResponse(403, "Session expired, please log in again", "SESSION_EXPIRED");
        } catch (JWTVerificationException e) {
            throw new ErrorResponse(403, "Invalid bearer token, this route requires auth", "INVALID_TOKEN");
        }

        return jwt.getSubject();
    }

    private static ECPublicKey readKey() {
        try {
            PemReader reader = new PemReader(new FileReader(PUB_PATH));
            PemObject object = reader.readPemObject();
            byte[] content = object.getContent();

            KeyFactory kf = KeyFactory.getInstance("EC");
            EncodedKeySpec spec = new X509EncodedKeySpec(content);

            return (ECPublicKey) kf.generatePublic(spec);
        } catch (IOException | GeneralSecurityException e) {
            e.printStackTrace();
        }

        return null;
    }
}
