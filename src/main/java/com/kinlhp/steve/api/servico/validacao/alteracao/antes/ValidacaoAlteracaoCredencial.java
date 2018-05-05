package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoCredencial;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveCredencial")
public class ValidacaoAlteracaoCredencial extends ValidacaoCredencial {

	private static final long serialVersionUID = 7616972640707802626L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Credencial.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Credencial) object;
		super.erros = errors;

		// TODO: 4/4/18 implementar design pattern que resolva essa má prática
		validarFuncionario();
	}
}
