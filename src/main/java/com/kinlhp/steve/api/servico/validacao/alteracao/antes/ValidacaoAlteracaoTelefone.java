package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Telefone;
import com.kinlhp.steve.api.repositorio.RepositorioTelefone;
import com.kinlhp.steve.api.servico.validacao.ValidacaoTelefone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveTelefone")
public class ValidacaoAlteracaoTelefone extends ValidacaoTelefone {

	private static final long serialVersionUID = 8031593055326108475L;

	public ValidacaoAlteracaoTelefone(@Autowired RepositorioTelefone repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Telefone.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Telefone) object;
		super.erros = errors;

		validarPessoa();
	}
}
