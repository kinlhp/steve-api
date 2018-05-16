package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Email;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEmail;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveEmail")
public class ValidacaoAlteracaoEmail extends ValidacaoEmail {

	private static final long serialVersionUID = -429508058634394249L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Email.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Email) object;
		super.erros = errors;

		validarPessoa();
	}
}
