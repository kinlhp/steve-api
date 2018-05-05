package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Telefone;
import com.kinlhp.steve.api.servico.validacao.ValidacaoTelefone;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveTelefone")
public class ValidacaoAlteracaoTelefone extends ValidacaoTelefone {

	private static final long serialVersionUID = -7933030061940994701L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Telefone.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Telefone) object;
		super.erros = errors;

		// TODO: 4/3/18 implementar design pattern que resolva essa má prática
		validarPessoa();
	}
}
