package br.com.pw.gestao.modules.candidates.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.exceptions.UserFoundExists;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;

@Service
public class CandidateUseCase {

    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CandidateUseCase (
        PasswordEncoder passwordEncoder,
        CandidateRepository candidateRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.candidateRepository = candidateRepository;
    }

    
    public CandidateEntity execute(CandidateEntity candidateEntity) {
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail())
            .ifPresent(user -> {
                throw new UserFoundExists();
            });
        var encodedPassword = this.passwordEncoder.encode(candidateEntity.getPassword());
        candidateEntity.setPassword(encodedPassword); 
        return this.candidateRepository.save(candidateEntity);
    }
}
