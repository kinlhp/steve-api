package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoMovimentacaoContaReceber
		extends ValidavelAbstrato<MovimentacaoContaReceber, BigInteger> {

	private static final long serialVersionUID = 2206791505106377928L;

	public ValidacaoMovimentacaoContaReceber(@Autowired RepositorioMovimentacaoContaReceber repositorio) {
		super(repositorio);
	}
}
