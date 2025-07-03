package br.com.pw.gestao.modules.candidates.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pw.gestao.modules.candidates.entities.ApplyJobEntity;

public interface ApplyJobRepository extends JpaRepository<ApplyJobEntity, String> {
    
}
