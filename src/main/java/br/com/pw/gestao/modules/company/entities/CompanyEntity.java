package br.com.pw.gestao.modules.company.entities;

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
import lombok.Data;

@Data
@Entity (name ="company")
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.UUID)
    @Length(max=36)
    @Schema(example="9e4f5d2a-8a30-4af1-a4de-2b238df76f21",requiredMode=Schema.RequiredMode.REQUIRED)
    private String id;

    @Pattern(regexp="\\S+", message="o campo [username] não pode conter espaços em brancos")
    @Schema(example="Castgroup", requiredMode=Schema.RequiredMode.REQUIRED)
    private String username;

    @Schema(example="Cast Group", requiredMode=Schema.RequiredMode.REQUIRED)
    private String nome;

    @Email(message="o campo [email] deve conter um email válido")
    @Schema(example="castrgroup.com.br")
    private String email;

    @NotBlank
    @Size(min=8,message="o campo [password] deve conter no mínimo 8 caracteres")
    @Schema(example="admin@1234", minLength=8, requiredMode=Schema.RequiredMode.REQUIRED)
    private String password;

    @Schema(example="www.castgroup.com.br")
    private String website;
    
    @Schema(example="Empresa de desenvolvimento de software e soluções")
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
