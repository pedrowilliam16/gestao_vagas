package br.com.pw.gestao.modules.candidates.useCases;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.JobRepository;


@Service
public class ListAllJobs {
    @Autowired
    private JobRepository jobRepository;
    public List<JobsEntity> execute(String filter){
       return this.jobRepository.findByDescriptionContainingIgnoreCase(filter);
    }
}
