package br.com.pw.gestao.modules.candidates.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name="candidatos")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Length(max=36)
    @Schema(example="3e4f5d2a-8a30-4ag1-a5ce-3b238df76f82",requiredMode=Schema.RequiredMode.REQUIRED)
    private String id;

    @Pattern(regexp="\\S+", message="o campo [username] não pode conter espaços em brancos")
    @Schema(example="pedrowilliam",requiredMode=Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(example="Pedro William")
    private String nome;

    @Email(message="o campo [email] deve conter um email válido")
    @Schema(example="pedrowilliam@gmail.com")
    private String email;

    @NotBlank
    @Size(min=8,message="o campo [password] deve conter no mínimo 8 caracteres")
    @Schema(example="admin@1234",minLength=8,description="Senha do candidato",requiredMode=Schema.RequiredMode.REQUIRED)
    private String password;
    
    @Schema(example="Desenvolvedor Java")
    private String description;
    private String curriculum;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
