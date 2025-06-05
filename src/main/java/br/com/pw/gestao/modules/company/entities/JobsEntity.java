package br.com.pw.gestao.modules.company.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="jobs")
public class JobsEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Length(max=36)
    private String id;

    private String description;

    @NotBlank(message="esse campo é obrigatório")
    private String level;
    private String benefits;


    @ManyToOne()
    @JoinColumn(name="id_company", insertable=false, updatable=false)
    
    private CompanyEntity companyEntity;

    @Column(name="id_company", nullable=false)
    private String companyId;


    @CreationTimestamp
    private LocalDateTime createdAt;

}
