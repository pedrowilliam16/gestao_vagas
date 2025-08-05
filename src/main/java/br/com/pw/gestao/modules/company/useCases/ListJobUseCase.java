package br.com.pw.gestao.modules.company.usecases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.JobRepository;

@Service
public class ListJobUseCase {
    @Autowired
    private JobRepository jobRepository;

    public List<JobsEntity> execute (String companyId){
     return this.jobRepository.findByCompanyId(companyId);
    }
}
