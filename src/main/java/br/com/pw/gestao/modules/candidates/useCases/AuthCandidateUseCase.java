package br.com.pw.gestao.modules.candidates.useCases;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.pw.gestao.exceptions.InvalidCredentials;
import br.com.pw.gestao.modules.candidates.dto.AuthCandidateRequestDTO;
import br.com.pw.gestao.modules.candidates.dto.AuthCandidateResponseDTO;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;

@Service
public class AuthCandidateUseCase {

   @Value("${security.token.secret.candidate}")
   private String secretKey;

   @Autowired
   private CandidateRepository candidateRepository;

   @Autowired
   private PasswordEncoder passwordEncoder;

   public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO candidateRequestDTO) {
       var candidate = this.candidateRepository.findByUsername(candidateRequestDTO.username())
           .orElseThrow(InvalidCredentials::new);

       var passwordMatches = passwordEncoder.matches(candidateRequestDTO.password(), candidate.getPassword());

       if (!passwordMatches) {
           throw new InvalidCredentials();
       }

       var expiresInMinutes = 30L;
       var expiration = Instant.now().plus(Duration.ofMinutes(expiresInMinutes));

       Algorithm algorithm = Algorithm.HMAC256(secretKey);
       var token = JWT.create()
           .withIssuer("javagas")
           .withSubject(candidate.getId())
           .withExpiresAt(expiration)
           .withClaim("roles", Arrays.asList("CANDIDATE"))
           .sign(algorithm);

       var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
           .withZone(ZoneId.systemDefault());
       var formattedExpiration = formatter.format(expiration);

       return AuthCandidateResponseDTO.builder()
           .acess_token(token)
           .expires_in_minutes(expiresInMinutes)
           .expires_at(formattedExpiration)
           .build();
   }
}
  