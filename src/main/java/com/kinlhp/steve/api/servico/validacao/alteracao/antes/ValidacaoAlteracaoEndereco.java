package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Endereco;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEndereco;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveEndereco")
public class ValidacaoAlteracaoEndereco extends ValidacaoEndereco {

	private static final long serialVersionUID = -4713438557763816219L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Endereco.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Endereco) object;
		super.erros = errors;

		// TODO: 4/4/18 implementar design pattern que resolva essa má prática
		validarPessoa();
	}
}
