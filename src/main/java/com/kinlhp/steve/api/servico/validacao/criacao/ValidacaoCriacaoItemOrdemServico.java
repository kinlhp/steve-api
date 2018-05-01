package com.kinlhp.steve.api.servico.validacao.criacao;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateItemOrdemServico")
public class ValidacaoCriacaoItemOrdemServico
		extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = -7948896574304437674L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ItemOrdemServico.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ItemOrdemServico) object;
		super.erros = errors;

		// TODO: 4/29/18 implementar design pattern que resolva essa má prática
		validarDataFinalizacaoPrevista();
	}
}
