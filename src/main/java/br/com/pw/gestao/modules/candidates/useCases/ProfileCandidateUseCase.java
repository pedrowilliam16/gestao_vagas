package br.com.pw.gestao.modules.candidates.usecases;

import org.springframework.stereotype.Component;

import br.com.pw.gestao.exceptions.UserNotFound;
import br.com.pw.gestao.modules.candidates.dto.ProfileCandidateDTO;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;

@Component
public class ProfileCandidateUseCase {

    private final CandidateRepository candidateRepository;

    public ProfileCandidateUseCase (
        CandidateRepository candidateRepository
        ) {
        this.candidateRepository=candidateRepository;
    }

    public ProfileCandidateDTO execute (String candidateId) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(()-> {
            throw new UserNotFound();
        });
       return ProfileCandidateDTO.builder()
        .id(candidate.getId())
        .username(candidate.getUsername())
        .nome(candidate.getNome())
        .description(candidate.getDescription())
        .email(candidate.getEmail())
        .build();
    }
}
