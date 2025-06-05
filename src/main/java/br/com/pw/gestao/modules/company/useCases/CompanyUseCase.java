package br.com.pw.gestao.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.exceptions.UserFoundExists;
import br.com.pw.gestao.modules.company.entities.CompanyEntity;
import br.com.pw.gestao.modules.company.repository.CompanyRepository;

@Service
public class CompanyUseCase {

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute ( CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(),companyEntity.getEmail()).ifPresent((user)-> {
            throw new UserFoundExists();
        });
        var passwordEncoder = this.passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(passwordEncoder);
        
        return companyRepository.save(companyEntity);
    } 
}
