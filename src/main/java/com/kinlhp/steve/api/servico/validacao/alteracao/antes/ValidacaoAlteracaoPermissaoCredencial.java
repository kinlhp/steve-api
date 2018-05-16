package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.PermissaoCredencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPermissaoCredencial;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSavePermissaoCredencial")
public class ValidacaoAlteracaoPermissaoCredencial
		extends ValidacaoPermissaoCredencial {

	private static final long serialVersionUID = 2746252150524220779L;

	@Override
	public boolean supports(Class<?> clazz) {
		return PermissaoCredencial.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (PermissaoCredencial) object;
		super.erros = errors;

		validarPermissao();
	}
}
