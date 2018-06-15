package com.kinlhp.steve.api.recurso;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.servico.ServicoContaPagar;
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
public class RecursoContaPagar implements Serializable {

	private static final long serialVersionUID = 7275261583990915903L;
	private final ServicoContaPagar servico;

	public RecursoContaPagar(@Autowired ServicoContaPagar servico) {
		this.servico = servico;
	}

	@PutMapping(path = {"/api/v1/contasPagar/{id}/estorno"})
	public ResponseEntity<ContaPagar> estornar(@PathVariable(value = "id") BigInteger id) {
		final ContaPagar contaPagarEstornada = servico.estornar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
