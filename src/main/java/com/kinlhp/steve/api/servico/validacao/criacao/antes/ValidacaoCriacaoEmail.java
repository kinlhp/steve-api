package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.Email;
import com.kinlhp.steve.api.servico.validacao.ValidacaoEmail;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateEmail")
public class ValidacaoCriacaoEmail extends ValidacaoEmail {

	private static final long serialVersionUID = 614876609609436154L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Email.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Email) object;
		super.erros = errors;

		// TODO: 4/4/18 implementar design pattern que resolva essa má prática
		validarPessoa();
	}
}
