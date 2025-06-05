package br.com.pw.gestao.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.company.dto.JobCreateDTO;
import br.com.pw.gestao.modules.company.entities.JobsEntity;
import br.com.pw.gestao.modules.company.useCases.JobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
public class JobController {
    
    @Autowired
    private  JobUseCase jobUseCase;

    @PostMapping("/job")
    @PreAuthorize("hasRole('COMPANY')")
    public JobsEntity create (@Valid @RequestBody JobCreateDTO jobCreateDTO ,HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");

       var jobsEntity = JobsEntity.builder()
        .description(jobCreateDTO.getDescription())
        .level(jobCreateDTO.getLevel())
        .benefits(jobCreateDTO.getBenefits())
        .companyId(companyId.toString())
        .build()
        ;
        return this.jobUseCase.execute(jobsEntity); 
    }
}
