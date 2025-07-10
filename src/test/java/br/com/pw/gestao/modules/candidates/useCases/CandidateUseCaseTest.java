package br.com.pw.gestao.modules.candidates.useCases;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pw.gestao.exceptions.UserFoundExists;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;
import br.com.pw.gestao.modules.candidates.usecases.CandidateUseCase;

@ExtendWith(MockitoExtension.class)
class CandidateUseCaseTest {

    @InjectMocks
    private CandidateUseCase candidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Test
    @DisplayName("Should not create candidate if username and email already exist")
    void should_not_create_candidate_if_username_and_email_already_exists(){
        String username="candidato";
        String email = "email";

        CandidateEntity candidateEntity = CandidateEntity.builder()
        .username(username)
        .email(email)
        .build();

        when(this.candidateRepository.findByUsernameOrEmail(username, email)).thenReturn(Optional.of(candidateEntity));

        assertThatThrownBy(() -> candidateUseCase.execute(candidateEntity))
            .isInstanceOf(UserFoundExists.class);
    }
}
