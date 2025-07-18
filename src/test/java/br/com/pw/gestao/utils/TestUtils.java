package br.com.pw.gestao.utils;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestUtils {
     public static String objectToJSON(Object obj){
        try {
            final ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateToken(String idCompany, String secret){
        Algorithm algorithm = Algorithm.HMAC256(secret);
        var expiresIn= Instant.now().plus(Duration.ofHours(2));

       return JWT.create().withIssuer("javagas")
        .withSubject(idCompany)
        .withExpiresAt(expiresIn)
        .withClaim("roles", Arrays.asList("company"))
        .sign(algorithm);

    }
}
