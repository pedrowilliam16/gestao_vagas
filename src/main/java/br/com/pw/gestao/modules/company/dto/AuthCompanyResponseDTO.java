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
public class AuthCompanyResponseDTO {
    @Schema(example="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJqYXZhZ2FzIiwic3ViIjoiMGY2NWVjMzYtZGJiZS00ODBlLTliNjUtNzM4NGM5YTg5YWVhIiwiZXhwIjoxNzUwNzkwNDQ3LCJyb2xlcyI6WyJDQU5ESURBVEUiXX0.-UAgvbinDuWIHGzmpIaxRUcTK9w5ch0hVGf0dBCn8NY")
    private String acess_token;

    @Schema(example="30")
    private Long expires_in_minutes;

    @Schema(example="24/06/2025 15:40")
    private String expires_at; 

}
