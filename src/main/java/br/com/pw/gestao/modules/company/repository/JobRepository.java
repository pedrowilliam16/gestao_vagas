package br.com.pw.gestao.modules.company.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pw.gestao.modules.company.entities.JobsEntity;

public interface  JobRepository extends JpaRepository<JobsEntity, String> {
    List<JobsEntity>findByDescriptionContainingIgnoreCase(String filter);
}
