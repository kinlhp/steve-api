package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterSaveItemOrdemServico")
public class AjusteAlteracaoItemOrdemServico extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = 29607266054888260L;

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
