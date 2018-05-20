package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoMovimentacaoContaPagar
		extends ValidavelAbstrato<MovimentacaoContaPagar, BigInteger> {

	private static final long serialVersionUID = 471071674903976863L;

	public ValidacaoMovimentacaoContaPagar(@Autowired RepositorioMovimentacaoContaPagar repositorio) {
		super(repositorio);
	}
}
