package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.Endereco;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEndereco;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateEndereco")
public class ValidacaoCriacaoEndereco extends ValidacaoEndereco {

	private static final long serialVersionUID = -2159018777172364334L;

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
