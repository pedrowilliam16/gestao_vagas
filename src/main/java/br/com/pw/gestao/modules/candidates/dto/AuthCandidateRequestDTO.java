package br.com.pw.gestao.modules.candidates.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class  AuthCandidateRequestDTO  {
    @Schema(example="pedrowilliam", requiredMode=Schema.RequiredMode.REQUIRED)
    String username; 
    @Schema(example="admin@1234", requiredMode=Schema.RequiredMode.REQUIRED, minLength=8)
    String password;
}
