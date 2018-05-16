package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoOrdem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateOrdem")
public class ValidacaoCriacaoOrdem extends ValidacaoOrdem {

	private static final long serialVersionUID = 8357947477581425240L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ordem.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Ordem) object;
		super.erros = errors;

		validarCliente();
		validarSituacao();
	}
}
