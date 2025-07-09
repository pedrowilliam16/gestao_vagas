package br.com.pw.gestao.modules.candidates.usecases;
import org.springframework.stereotype.Service;

import br.com.pw.gestao.exceptions.JobNotFound;
import br.com.pw.gestao.exceptions.UserNotFound;
import br.com.pw.gestao.modules.candidates.entities.ApplyJobEntity;
import br.com.pw.gestao.modules.candidates.repository.ApplyJobRepository;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;
import br.com.pw.gestao.modules.company.repository.JobRepository;

@Service
public class ApplyJobCandidateUseCase {

    private final ApplyJobRepository applyJobRepository;
    private final CandidateRepository candidateRepository;
    private final JobRepository jobRepository;

    public ApplyJobCandidateUseCase (
        JobRepository jobRepository,
        CandidateRepository candidateRepository,
        ApplyJobRepository applyJobRepository
    ) {
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
        this.applyJobRepository = applyJobRepository;
    }

    
    public ApplyJobEntity execute(String idCandidate, String idJob){
        /* 
         * verificar se o candidato existe
         * verificar se a vaga existe
         * inscrever o candidato na vaga
        */

        this.candidateRepository.findById(idCandidate).orElseThrow(()->{
            throw new UserNotFound();
        });

        this.jobRepository.findById(idJob).orElseThrow(()->{
            throw new JobNotFound();
        });
        
        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build()
        ;
        applyJob = applyJobRepository.save(applyJob);
        return applyJob;
        

    }
}
