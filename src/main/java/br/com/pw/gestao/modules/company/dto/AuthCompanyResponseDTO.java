package br.com.pw.gestao.modules.company.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthCompanyResponseDTO {

    @JsonProperty("acess_token")
    @Schema(example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @JsonProperty("expires_in_minutes")
    @Schema(example = "30")
    private Long expiresInMinutes;

    @JsonProperty("expires_at")
    @Schema(example = "24/06/2025 15:40")
    private String expiresAt;

    private List<String> roles;
}
