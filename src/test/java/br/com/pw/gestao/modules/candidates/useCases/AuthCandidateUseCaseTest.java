package br.com.pw.gestao.modules.candidates.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import br.com.pw.gestao.exceptions.InvalidCredentials;
import br.com.pw.gestao.modules.candidates.dto.AuthCandidateRequestDTO;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;
import br.com.pw.gestao.modules.candidates.usecases.AuthCandidateUseCase;

@ExtendWith(MockitoExtension.class)
class AuthCandidateUseCaseTest {

    @InjectMocks
    private AuthCandidateUseCase authCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("Should not create be token with candidate not found")
    void should_not_create_be_token_with_candidate_not_found(){
        String candidate= UUID.randomUUID().toString();

        when(candidateRepository.findByUsername(candidate)).thenReturn(Optional.empty());

        AuthCandidateRequestDTO dto = new AuthCandidateRequestDTO();
        dto.setUsername(candidate);
        dto.setPassword("admin@1234");

        assertThatThrownBy(()-> authCandidateUseCase.execute(dto)).isInstanceOf(InvalidCredentials.class);
        
    }

    @Test
    @DisplayName("Should not create a token with password is incorrect")
    void should_not_create_be_token_with_password_is_incorrect(){
        String username="username";
        String password="password";
        String encodedPassword="djfabde3@4criptografada";

       var candidate = CandidateEntity.builder()
        .username(username)
        .password(encodedPassword)
        .build();

        when(candidateRepository.findByUsername(username)).thenReturn(Optional.of(candidate));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

        AuthCandidateRequestDTO dto = AuthCandidateRequestDTO.builder()
        .username(username)
        .password(password)
        .build();

        assertThatThrownBy(()-> authCandidateUseCase.execute(dto)).isInstanceOf(InvalidCredentials.class);
    }

    @Test
    @DisplayName("Should create token when credentials are correct")
    void should_create_token_when_credentials_are_correct(){
        String username="username";
        String password="password";
        String encodedPassword="djfabde3@4criptografada";

       var candidate = CandidateEntity.builder()
        .username(username)
        .password(encodedPassword)
        .build();

        when(candidateRepository.findByUsername(username)).thenReturn(Optional.of(candidate));
        when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

        AuthCandidateRequestDTO dto = AuthCandidateRequestDTO.builder()
        .username(username)
        .password(password)
        .build();

        ReflectionTestUtils.setField(authCandidateUseCase, "secretKey", "chave-secreta-segura");

        var result = authCandidateUseCase.execute(dto);

        assertThat(result).isNotNull();
        assertThat(result.getAcessToken()).isNotBlank();
        assertThat(result.getExpiresInMinutes()).isEqualTo(30L);
        assertThat(result.getExpiresAt()).isNotBlank();

    }

}

