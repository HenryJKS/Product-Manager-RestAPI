package br.com.fiap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Clientes de Produtos API", version = "1.0", description = "Informações da API de Clientes"))
public class ClientesProdutosRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientesProdutosRestApiApplication.class, args);
	}

}
