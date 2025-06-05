package br.com.pw.gestao.modules.company.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

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
    private String id;

    @Pattern(regexp="\\S+", message="o campo [username] não pode conter espaços em brancos")
    private String username;

    private String nome;

    @Email(message="o campo [email] deve conter um email válido")
    private String email;

    @NotBlank
    @Size(min=8,message="o campo [password] deve conter no mínimo 8 caracteres")
    private String password;

    private String website;
    
    private String description;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
