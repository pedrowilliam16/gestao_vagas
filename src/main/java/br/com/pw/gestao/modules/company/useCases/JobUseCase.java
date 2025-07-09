package br.com.pw.gestao.modules.company.usecases;

import org.springframework.stereotype.Service;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.JobRepository;


@Service
public class JobUseCase {
    
    private final JobRepository jobRepository;

    public JobUseCase (
        JobRepository jobRepository
    ) {
        this.jobRepository=jobRepository;
    }

    public JobsEntity execute (JobsEntity jobsEntity) {
        return this.jobRepository.save(jobsEntity);
    }


}
