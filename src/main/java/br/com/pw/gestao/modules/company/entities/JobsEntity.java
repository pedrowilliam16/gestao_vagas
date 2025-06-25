package br.com.pw.gestao.modules.company.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example="7e4f5d2a-8a30-4af1-a5ce-3b238df76f91",requiredMode=Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(example="Desenvolvedor Backend Java",requiredMode=Schema.RequiredMode.REQUIRED)
    private String description;

    @NotBlank(message="esse campo é obrigatório")
     @Schema(example="SÊNIOR",requiredMode=Schema.RequiredMode.REQUIRED)
    private String level;

    @Schema(example="Vale Refeição, Plano de Saúde, Home Office",requiredMode=Schema.RequiredMode.REQUIRED)
    private String benefits;


    @ManyToOne()
    @JoinColumn(name="id_company", insertable=false, updatable=false)
    
    private CompanyEntity companyEntity;

    @Column(name="id_company", nullable=false)
    private String companyId;


    @CreationTimestamp
    private LocalDateTime createdAt;

}
