package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterSaveMovimentacaoContaPagar")
public class AjusteAlteracaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = -3772152682423087744L;
	private final RepositorioContaPagar repositorioContaPagar;

	public AjusteAlteracaoMovimentacaoContaPagar(@Autowired RepositorioMovimentacaoContaPagar repositorio,
	                                             @Autowired RepositorioContaPagar repositorioContaPagar) {
		super(repositorio);
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

		reabrirContaPagar();
	}

	private void reabrirContaPagar() {
		final ContaPagar contaPagar = super.dominio.getContaPagar();
		if (contaPagar.hasSaldoDevedor()) {
			if (contaPagar.hasMontantePago()) {
				contaPagar.setSituacao(ContaPagar.Situacao.AMORTIZADO);
			} else {
				contaPagar.setSituacao(ContaPagar.Situacao.ABERTO);
			}
			repositorioContaPagar.saveAndFlush(contaPagar);
		}
	}
}
