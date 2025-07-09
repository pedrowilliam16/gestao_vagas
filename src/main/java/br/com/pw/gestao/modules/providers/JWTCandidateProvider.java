package br.com.pw.gestao.modules.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

@Service
public class JWTCandidateProvider {

    @Value ("${security.token.secret.candidate}")
    String secretKey;

    public DecodedJWT execute (String token) {
        token = token.replace("Bearer ", "");
        
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            return JWT.require(algorithm)
            .build()
            .verify(token)
            ;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            return null;
        }

        
    }
}
