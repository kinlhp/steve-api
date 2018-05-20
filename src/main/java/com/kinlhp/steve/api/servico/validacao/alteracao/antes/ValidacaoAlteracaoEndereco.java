package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Endereco;
import com.kinlhp.steve.api.repositorio.RepositorioEndereco;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEndereco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveEndereco")
public class ValidacaoAlteracaoEndereco extends ValidacaoEndereco {

	private static final long serialVersionUID = 123632814064974421L;

	public ValidacaoAlteracaoEndereco(@Autowired RepositorioEndereco repositorio) {
		super(repositorio);
	}

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
