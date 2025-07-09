package br.com.pw.gestao.modules.company.usecases;

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
import br.com.pw.gestao.modules.company.dto.AuthCompanyRequestDTO;
import br.com.pw.gestao.modules.company.dto.AuthCompanyResponseDTO;
import br.com.pw.gestao.modules.company.repository.CompanyRepository;


@Service
public class AuthCompanyUseCase {
    
    @Value("${security.token.secret}")
    private String secretKey;

    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public AuthCompanyUseCase (
        PasswordEncoder passwordEncoder,
        CompanyRepository companyRepository
    ) {
        this.passwordEncoder=passwordEncoder;
        this.companyRepository=companyRepository;
    }

    public AuthCompanyResponseDTO execute (AuthCompanyRequestDTO authCompanyRequestDTO) {
       var company = this.companyRepository.findByUsername(authCompanyRequestDTO.getUsername()).orElseThrow(InvalidCredentials::new); 
          
       var passwordmatches = passwordEncoder.matches(authCompanyRequestDTO.getPassword(), company.getPassword());

       if(!passwordmatches) {
            throw new InvalidCredentials();
       }
       var expiresInMinutes = 60L;
       var expiration = Instant.now().plus(Duration.ofMinutes(expiresInMinutes));

      Algorithm algorithm = Algorithm.HMAC256(secretKey);
       var token = JWT.create()
       .withIssuer("javagas")
       .withSubject(company.getId())
       .withClaim("roles",Arrays.asList("COMPANY"))
       .sign(algorithm)
       ;

       var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
           .withZone(ZoneId.systemDefault());
       var formattedExpiration = formatter.format(expiration);

       return AuthCompanyResponseDTO.builder()
           .accessToken(token)
           .expiresInMinutes(expiresInMinutes)
           .expiresAt(formattedExpiration)
           .build();
        }
    }
