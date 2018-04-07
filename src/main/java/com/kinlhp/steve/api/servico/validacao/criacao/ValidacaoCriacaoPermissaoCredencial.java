package com.kinlhp.steve.api.servico.validacao.criacao;

import com.kinlhp.steve.api.dominio.PermissaoCredencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPermissaoCredencial;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreatePermissaoCredencial")
public class ValidacaoCriacaoPermissaoCredencial
		extends ValidacaoPermissaoCredencial {

	private static final long serialVersionUID = 4421594552057857512L;

	@Override
	public boolean supports(Class<?> clazz) {
		return PermissaoCredencial.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (PermissaoCredencial) object;
		super.erros = errors;

		// TODO: 4/6/18 implementar design pattern que resolva essa má prática
		validarPermissao();
	}
}
