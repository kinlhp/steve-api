package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterCreateItemOrdemServico")
public class AjusteCriacaoItemOrdemServico extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = 5012847036174365463L;

	public AjusteCriacaoItemOrdemServico(@Autowired RepositorioOrdem repositorioOrdem) {
		super(repositorioOrdem);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ItemOrdemServico.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ItemOrdemServico) object;
		super.erros = errors;

		finalizarOuReabrirOrdem();
	}
}
