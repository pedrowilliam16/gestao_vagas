package br.com.pw.gestao.modules.company.usecases;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.exceptions.UserFoundExists;
import br.com.pw.gestao.modules.company.entities.CompanyEntity;
import br.com.pw.gestao.modules.company.repository.CompanyRepository;

@Service
public class CompanyUseCase {

    private final PasswordEncoder passwordEncoder;
    private final CompanyRepository companyRepository;

    public CompanyUseCase (
        PasswordEncoder passwordEncoder,
        CompanyRepository companyRepository
    ) {
        this.passwordEncoder=passwordEncoder;
        this.companyRepository=companyRepository;
    }

    public CompanyEntity execute ( CompanyEntity companyEntity) {
        this.companyRepository.findByUsernameOrEmail(companyEntity.getUsername(),companyEntity.getEmail()).ifPresent(user-> {
            throw new UserFoundExists();
        });
        var encodedPassword = this.passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(encodedPassword);
        
        return companyRepository.save(companyEntity);
    } 
}
