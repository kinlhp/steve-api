package com.kinlhp.steve.api.recurso;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.servico.ServicoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.math.BigInteger;

@RequestMapping
@RestController
public class RecursoContaReceber implements Serializable {

	private final ServicoContaReceber servico;

	public RecursoContaReceber(@Autowired ServicoContaReceber servico) {
		this.servico = servico;
	}

	@PutMapping(path = {"/api/v1/contasReceber/{id}/estorno"})
	public ResponseEntity<ContaReceber> estornar(@PathVariable(value = "id") BigInteger id) {
		final ContaReceber contaReceberEstornada = servico.estornar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
