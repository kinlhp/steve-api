package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterCreateMovimentacaoContaPagar")
public class AjusteCriacaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = -5483238762007327037L;
	private final RepositorioContaPagar repositorioContaPagar;

	public AjusteCriacaoMovimentacaoContaPagar(@Autowired RepositorioContaPagar repositorioContaPagar) {
		this.repositorioContaPagar = repositorioContaPagar;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaPagar) object;
		super.erros = errors;

		baixarContaPagar();
	}

	private void baixarContaPagar() {
		final ContaPagar contaPagar = super.dominio.getContaPagar();
		if (!contaPagar.hasSaldoDevedor()
				&& !ContaPagar.Situacao.BAIXADO.equals(contaPagar.getSituacao())) {
			contaPagar.setSituacao(ContaPagar.Situacao.BAIXADO);
			repositorioContaPagar.saveAndFlush(contaPagar);
		}
	}
}
