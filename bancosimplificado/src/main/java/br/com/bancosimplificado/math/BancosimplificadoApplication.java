package br.com.bancosimplificado.math;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Banco Simplificado API", description = "Teste técnico de back-end") )
public class BancosimplificadoApplication {

	public static void main(String[] args) {
		SpringApplication.run(BancosimplificadoApplication.class, args);
	}

}
