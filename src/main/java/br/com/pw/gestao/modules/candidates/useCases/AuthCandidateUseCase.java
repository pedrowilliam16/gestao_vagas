package br.com.pw.gestao.modules.candidates.usecases;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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

    private final PasswordEncoder passwordEncoder;
    private final CandidateRepository candidateRepository;

    @Value("${security.token.secret.candidate}")
    private String secretKey;

    public AuthCandidateUseCase (
        CandidateRepository candidateRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

   public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO candidateRequestDTO) {
       var candidate = this.candidateRepository.findByUsername(candidateRequestDTO.getUsername()).orElseThrow(InvalidCredentials::new);
       
       var passwordMatches = passwordEncoder.matches(candidateRequestDTO.getPassword(), candidate.getPassword());

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
           .acessToken(token)
           .expiresInMinutes(expiresInMinutes)
           .expiresAt(formattedExpiration)
           .build();
   }
}
  