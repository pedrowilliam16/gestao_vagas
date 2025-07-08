package br.com.pw.gestao.modules.candidates.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.pw.gestao.exceptions.UserNotFound;
import br.com.pw.gestao.modules.candidates.dto.ProfileCandidateDTO;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;

@Component
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateDTO execute (String candidateId) {
        var candidate = this.candidateRepository.findById(candidateId).orElseThrow(()-> {
            throw new UserNotFound();
        });
       var profileCandidateDTO = ProfileCandidateDTO.builder()
        .id(candidate.getId())
        .username(candidate.getUsername())
        .nome(candidate.getNome())
        .description(candidate.getDescription())
        .email(candidate.getEmail())
        .build();
        
        return profileCandidateDTO;
    }
}
