package br.com.pw.gestao.modules.company.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.company.dto.AuthCompanyRequestDTO;
import br.com.pw.gestao.modules.company.dto.AuthCompanyResponseDTO;
import br.com.pw.gestao.modules.company.usecases.AuthCompanyUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/company")
@Tag(name="Autentificação da Empresa", description="Token da Empresa")
public class AuthCompanyController {

    private final AuthCompanyUseCase authCompanyUseCase;

    public AuthCompanyController (
        AuthCompanyUseCase authCompanyUseCase
    ){
        this.authCompanyUseCase=authCompanyUseCase;
    }

    @PostMapping("/auth")
    @Operation(summary="Token da empresa", description="Essa função é responsável por gerar o token da empresa")
    @ApiResponse(
        responseCode="200",
        content=@Content(schema=@Schema(implementation=AuthCompanyResponseDTO.class))
    )
    @ApiResponse(
        responseCode="401", 
        description="Username/Password incorret"
    
    )

    public ResponseEntity<Object> create (@RequestBody AuthCompanyRequestDTO authCompanyDTO) {
        try {
            var result = this.authCompanyUseCase.execute(authCompanyDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
    
}
