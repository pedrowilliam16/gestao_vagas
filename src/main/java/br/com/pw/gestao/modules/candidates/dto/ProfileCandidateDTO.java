package br.com.pw.gestao.modules.candidates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateDTO {
    @Schema(example="3e4f5d2a-8a30-4ag1-a5ce-3b238df76f82",requiredMode=Schema.RequiredMode.REQUIRED)
    private String id;

    @Schema(example="pedrowilliam")
    private String username;
    @Schema(example="pedrowilliam@gmail.com")
    private String email;
    @Schema(example="Desenvolvedor Java")
    private String description;

    @Schema(example="Pedro William")
    private String nome;

}
