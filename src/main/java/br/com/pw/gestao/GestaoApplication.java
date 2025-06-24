package br.com.pw.gestao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/* 
@OpenAPIDefinition(
	info = @Info(title = "Gestão de vagas", description = "API responsável pela gestão de vagas",version = "1")
)
@SecurityScheme(name="jwt_auth",scheme="bearer", bearerFormat="JWT", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)
*/
public class GestaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestaoApplication.class, args);
	}

}
