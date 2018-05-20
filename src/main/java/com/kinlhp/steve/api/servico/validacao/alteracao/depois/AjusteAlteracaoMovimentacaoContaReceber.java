package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterSaveMovimentacaoContaReceber")
public class AjusteAlteracaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -2701703791536747319L;
	private final RepositorioContaReceber repositorioContaReceber;

	public AjusteAlteracaoMovimentacaoContaReceber(@Autowired RepositorioMovimentacaoContaReceber repositorio,
	                                               @Autowired RepositorioContaReceber repositorioContaReceber) {
		super(repositorio);
		this.repositorioContaReceber = repositorioContaReceber;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaReceber) object;
		super.erros = errors;

		reabrirContaReceber();
	}

	private void reabrirContaReceber() {
		final ContaReceber contaReceber = super.dominio.getContaReceber();
		if (contaReceber.hasSaldoDevedor()) {
			if (contaReceber.hasMontantePago()) {
				contaReceber.setSituacao(ContaReceber.Situacao.AMORTIZADO);
			} else {
				contaReceber.setSituacao(ContaReceber.Situacao.ABERTO);
			}
			repositorioContaReceber.saveAndFlush(contaReceber);
		}
	}
}
