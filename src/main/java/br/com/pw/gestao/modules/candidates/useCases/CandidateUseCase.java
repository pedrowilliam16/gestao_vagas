package br.com.pw.gestao.modules.candidates.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.exceptions.UserFoundExists;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;

@Service
public class CandidateUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateRepository candidateRepository;

    public CandidateEntity execute (CandidateEntity candidateEntity){
        this.candidateRepository.findByUsernameOrEmail(candidateEntity.getUsername(), candidateEntity.getEmail()).ifPresent((user)->{
            throw new UserFoundExists();
        });
          var passwordEncoder = this.passwordEncoder.encode(candidateEntity.getPassword());
          candidateEntity.setPassword(passwordEncoder); 
          return this.candidateRepository.save(candidateEntity);
    }
}
