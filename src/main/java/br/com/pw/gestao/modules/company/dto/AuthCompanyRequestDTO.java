package br.com.pw.gestao.modules.company.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyRequestDTO {
    @Schema(example="Castgroup", requiredMode=Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(example="admin@1234", minLength=8, requiredMode=Schema.RequiredMode.REQUIRED)
    private String password;
}
