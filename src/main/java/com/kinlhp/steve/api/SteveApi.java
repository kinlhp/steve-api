package com.kinlhp.steve.api;

import com.kinlhp.steve.api.auditoria.ImplementacaoAuditoriaConsciente;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.Serializable;

@EnableJpaAuditing(
		auditorAwareRef = "provedorAuditoriaConsciente",
		modifyOnCreate = false
)
@SpringBootApplication
public class SteveApi implements Serializable {

	private static final long serialVersionUID = 7534352172178598836L;
	private final ImplementacaoAuditoriaConsciente implementacaoAuditoriaConsciente;

	public SteveApi(@Autowired ImplementacaoAuditoriaConsciente implementacaoAuditoriaConsciente) {
		this.implementacaoAuditoriaConsciente = implementacaoAuditoriaConsciente;
	}

	public static void main(String[] args) {
		SpringApplication.run(SteveApi.class, args);
	}

	@Bean
	AuditorAware<Credencial> provedorAuditoriaConsciente() {
		return this.implementacaoAuditoriaConsciente;
	}
}
