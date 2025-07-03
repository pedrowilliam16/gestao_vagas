package br.com.pw.gestao.modules.candidates.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import br.com.pw.gestao.modules.company.entities.JobsEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="apply_jobs")
public class ApplyJobEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name="candidate_id", insertable=false,updatable=false)
    private CandidateEntity candidateEntity;

    @ManyToOne
    @JoinColumn(name="job_id", insertable=false,updatable=false)
    private JobsEntity jobsEntity;

    @Column(name="candidate_id")
    private String candidateId;

    @Column(name="job_id")
    private String jobId;

    @CreationTimestamp
    private LocalDateTime createdAt;


}
