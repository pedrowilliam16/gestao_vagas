package br.com.pw.gestao.modules.company.useCases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.JobRepository;


@Service
public class JobUseCase {
    
    @Autowired
    private JobRepository jobRepository;

    public JobsEntity execute (JobsEntity jobsEntity) {
        return this.jobRepository.save(jobsEntity);
    }


}
