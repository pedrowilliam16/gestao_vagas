package br.com.pw.gestao.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobCreateDTO {
    @Schema(example="Desenvolvedor Backend Java",requiredMode=Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(example="SÊNIOR",requiredMode=Schema.RequiredMode.REQUIRED)
    private String level;

    @Schema(example="Vale Refeição, Plano de Saúde, Home Office",requiredMode=Schema.RequiredMode.REQUIRED)
    private String benefits;
}
