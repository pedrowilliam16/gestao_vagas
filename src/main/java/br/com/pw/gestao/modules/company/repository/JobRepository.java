package br.com.pw.gestao.modules.company.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pw.gestao.modules.company.entities.JobsEntity;

public interface  JobRepository extends JpaRepository<JobsEntity, String> {
    
}
