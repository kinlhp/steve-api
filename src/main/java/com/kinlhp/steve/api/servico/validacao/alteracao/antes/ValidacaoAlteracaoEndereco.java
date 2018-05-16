package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Endereco;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEndereco;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveEndereco")
public class ValidacaoAlteracaoEndereco extends ValidacaoEndereco {

	private static final long serialVersionUID = -6051946257013651726L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Endereco.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Endereco) object;
		super.erros = errors;

		validarPessoa();
	}
}
