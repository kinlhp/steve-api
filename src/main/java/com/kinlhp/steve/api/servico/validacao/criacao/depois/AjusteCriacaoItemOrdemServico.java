package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterCreateItemOrdemServico")
public class AjusteCriacaoItemOrdemServico extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = -439583356192499480L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ItemOrdemServico.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ItemOrdemServico) object;
		super.erros = errors;

		// TODO: 5/6/18 implementar design pattern que resolva essa má prática
		finalizarOuReabrirOrdem();
	}
}
