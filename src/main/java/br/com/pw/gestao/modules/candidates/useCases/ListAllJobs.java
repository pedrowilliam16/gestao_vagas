package br.com.pw.gestao.modules.candidates.usecases;

import java.util.List;

import org.springframework.stereotype.Service;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.JobRepository;


@Service
public class ListAllJobs {
    
    private final JobRepository jobRepository;

    public ListAllJobs (
        JobRepository jobRepository
    ) {
        this.jobRepository=jobRepository;
    }

    public List<JobsEntity> execute(String filter){
       return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
