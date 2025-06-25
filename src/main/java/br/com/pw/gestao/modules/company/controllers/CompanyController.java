package br.com.pw.gestao.modules.company.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.company.entities.CompanyEntity;
import br.com.pw.gestao.modules.company.useCases.CompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/company")
@Tag(name="Empresa", description="Cadastro de empresa")
public class CompanyController {
    
    @Autowired
    private CompanyUseCase companyUseCase;

    @PostMapping ("/")
     @Operation(summary="Criar perfil da empresa", description="Essa função é responsável por cadastrar a empresa")
    @ApiResponses({
        @ApiResponse(responseCode="200", content={
            @Content(
                schema=@Schema(implementation=CompanyEntity.class)
            )
        }),
        @ApiResponse(responseCode="400", description="Usuário já existe")
    })
    public ResponseEntity<Object> create (@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = this.companyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
