package br.com.pw.gestao.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.company.dto.JobCreateDTO;
import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.usecases.JobUseCase;
import br.com.pw.gestao.modules.company.usecases.ListJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name="Vagas", description="Cadastro de vagas")
public class JobController {
    
    private final JobUseCase jobUseCase;

    public JobController (
        JobUseCase jobUseCase
    ) {
        this.jobUseCase=jobUseCase;
    }

    @Autowired
    private ListJobUseCase listJobUseCase;


    @PostMapping("/job")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary="Criar vagas", description="Esse método é o endpoint para criação de uma nova vaga de emprego associada a uma empresa")
    @SecurityRequirement(name="jwt_auth")
    @ApiResponse(
        responseCode="200", 
        content=@Content(schema=@Schema(implementation=JobsEntity.class))
    )
    public ResponseEntity<Object> create (@Valid @RequestBody JobCreateDTO jobCreateDTO ,HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        try {
            var jobsEntity = JobsEntity.builder()
            .description(jobCreateDTO.getDescription())
            .level(jobCreateDTO.getLevel())
            .benefits(jobCreateDTO.getBenefits())
            .companyId(companyId.toString())
            .build()
            ;
            var result = this.jobUseCase.execute(jobsEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(summary="Listar vagas", description="Esse método é o endpoint para listar as vagas da empresa")
    @SecurityRequirement(name="jwt_auth")
    @ApiResponse(
        responseCode="200", 
        content=@Content(schema=@Schema(implementation=JobsEntity.class))
    )
    public ResponseEntity<Object> listByCompany (HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

        var result = this.listJobUseCase.execute(companyId.toString());

        return ResponseEntity.ok().body(result);
    }

}
