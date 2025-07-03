package br.com.pw.gestao.modules.candidates.useCases;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.pw.gestao.exceptions.JobNotFound;
import br.com.pw.gestao.exceptions.UserNotFound;
import br.com.pw.gestao.modules.candidates.entities.ApplyJobEntity;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.repository.ApplyJobRepository;
import br.com.pw.gestao.modules.candidates.repository.CandidateRepository;
import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.repository.CompanyRepository;
import br.com.pw.gestao.modules.company.repository.JobRepository;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {
    
    @InjectMocks //Da camada que estou fazendo o teste
    private ApplyJobCandidateUseCase applyJobCandidateUseCase;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private CompanyRepository companyRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private ApplyJobRepository applyJobRepository;

    @Test
    @DisplayName("Should not be able to apply job with candidate not found")
    public void should_not_be_able_to_apply_job_with_candidate_not_found(){
        try {
            applyJobCandidateUseCase.execute(null, null);

        } catch (Exception e) {
            assertThat(e).isInstanceOf(UserNotFound.class);
        }

    }
    @Test
    @DisplayName("Should not be able to apply job with job not found")
    public void should_not_be_able_to_apply_job_with_job_not_found(){
        var idCandidate = UUID.randomUUID().toString();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);
       
       when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(candidate));

       try{
            applyJobCandidateUseCase.execute(idCandidate, null);
       } catch(Exception e) {
            assertThat(e).isInstanceOf(JobNotFound.class);
       }
    }

    @Test
    @DisplayName("Should be able to create a new apply job")
    public void should_be_able_to_create_a_new_apply_job(){
        var idCandidate = UUID.randomUUID().toString();
        var idJob = UUID.randomUUID().toString();

        var applyJob = ApplyJobEntity.builder()
        .candidateId(idCandidate)
        .jobId(idJob)
        .build();
        
        var applyJobCreated = ApplyJobEntity.builder().id(UUID.randomUUID().toString()).build();

        when(candidateRepository.findById(idCandidate)).thenReturn(Optional.of(new CandidateEntity()));
        when(jobRepository.findById(idJob)).thenReturn(Optional.of(new JobsEntity()));

    

        when(applyJobRepository.save(applyJob)).thenReturn(applyJobCreated);

        var result = applyJobCandidateUseCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());

    }
}
