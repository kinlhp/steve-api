package com.kinlhp.steve.api.servico.validacao.criacao;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateContaReceber")
public class ValidacaoCriacaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = -3860541141413561967L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaReceber) object;
		super.erros = errors;

		// TODO: 4/30/18 implementar design pattern que resolva essa má prática
		validarDataVencimento();
		validarOrdem();
		validarSituacao();
	}
}
