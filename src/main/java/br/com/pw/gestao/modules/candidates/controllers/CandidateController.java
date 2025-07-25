package br.com.pw.gestao.modules.candidates.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.pw.gestao.modules.candidates.dto.ProfileCandidateDTO;
import br.com.pw.gestao.modules.candidates.entities.CandidateEntity;
import br.com.pw.gestao.modules.candidates.usecases.ApplyJobCandidateUseCase;
import br.com.pw.gestao.modules.candidates.usecases.CandidateUseCase;
import br.com.pw.gestao.modules.candidates.usecases.ListAllJobs;
import br.com.pw.gestao.modules.candidates.usecases.ProfileCandidateUseCase;
import br.com.pw.gestao.modules.company.entities.JobsEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/candidate")
@Tag(name="Candidato", description="Informações do Candidato")
public class CandidateController {

    private final CandidateUseCase candidateUserCase;
    private final ProfileCandidateUseCase profileCandidateUseCase;
    private final ListAllJobs listAllJobs;
    private final ApplyJobCandidateUseCase applyJobCandidateUseCase;

    public CandidateController(
        CandidateUseCase candidateUserCase,
        ProfileCandidateUseCase profileCandidateUseCase,
        ListAllJobs listAllJobs,
        ApplyJobCandidateUseCase applyJobCandidateUseCase
    ) {
        this.candidateUserCase = candidateUserCase;
        this.profileCandidateUseCase = profileCandidateUseCase;
        this.listAllJobs = listAllJobs;
        this.applyJobCandidateUseCase = applyJobCandidateUseCase;
    }


    @PostMapping("/")
    @Operation(summary="Cadastro de candidato", description="Essa função é responsável por cadastrar os candidatos")
    @ApiResponse(
        responseCode="200", 
        content=@Content(schema=@Schema(implementation=CandidateEntity.class))
    )
    @ApiResponse(
        responseCode="400", 
        description="Usuário já existe"
    )

    public ResponseEntity<Object> create (@Valid @RequestBody CandidateEntity candidateEntity) {        
        try {
            var result = this.candidateUserCase.execute(candidateEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary="Perfil do candidato", description="Essa função é responsável por mostrar o perfil do candidato")
    @SecurityRequirement(name="jwt_auth")
    @ApiResponse(
        responseCode="200",
        content=@Content(schema=@Schema(implementation=ProfileCandidateDTO.class))
    )
    @ApiResponse(
        responseCode="400",
        description="User not found"
    )
    
    public ResponseEntity<Object> get(HttpServletRequest request){
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(idCandidate.toString());
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/job")
    @PreAuthorize("hasRole('CANDIDATE')")
    @Operation(summary="Listagem de vagas diponível para o candidato", description="Essa função é responsável por listar todas as vagas baseado no filtro")
    @ApiResponse(
        responseCode="200", 
        content=@Content(array= @ArraySchema(schema=@Schema(implementation=JobsEntity.class)))
    )

    @SecurityRequirement(name="jwt_auth")
    public List<JobsEntity> listAllJobs (@RequestParam String filter) {
        return this.listAllJobs.execute(filter); 
    }


    @PostMapping("/job/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    @SecurityRequirement(name="jwt_auth")
    public ResponseEntity<Object> applyJob(HttpServletRequest request, @RequestBody String idJob){
        var idCandidate = request.getAttribute("candidate_id");

        try {
            var result = applyJobCandidateUseCase.execute(idCandidate.toString(), idJob);
            return ResponseEntity.ok().body(result); 
            
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
