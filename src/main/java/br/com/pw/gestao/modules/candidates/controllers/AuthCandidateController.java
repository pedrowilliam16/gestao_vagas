package br.com.pw.gestao.modules.candidates.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.candidates.dto.AuthCandidateRequestDTO;
import br.com.pw.gestao.modules.candidates.dto.AuthCandidateResponseDTO;
import br.com.pw.gestao.modules.candidates.usecases.AuthCandidateUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/candidate")
@Tag(name="Autentificação do Candidato", description="Token do Candidato")
public class AuthCandidateController {
    
    private final AuthCandidateUseCase authCandidateUseCase;
    public AuthCandidateController(AuthCandidateUseCase authCandidateUseCase) {
        this.authCandidateUseCase = authCandidateUseCase;
    }

    @PostMapping("/auth")
    @Operation(summary="Token do candidato", description="Essa função é responsável por gerar o token do candidato")
    @ApiResponse(
        responseCode="200", 
        content=@Content(schema=@Schema(implementation=AuthCandidateResponseDTO.class))
    )
    @ApiResponse(
        responseCode="401", 
        description="Username/Password incorret"
    )

    public ResponseEntity<Object> create (@RequestBody AuthCandidateRequestDTO authCandidateRequestDTO) {
        try {
            var result =  this.authCandidateUseCase.execute(authCandidateRequestDTO);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
