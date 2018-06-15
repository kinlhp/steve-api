package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Set;

@Component(value = "afterCreateMovimentacaoContaPagar")
public class AjusteCriacaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = -3291885104221880616L;
	private final RepositorioContaPagar repositorioContaPagar;

	public AjusteCriacaoMovimentacaoContaPagar(@Autowired RepositorioMovimentacaoContaPagar repositorio,
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

		amortizarOuBaixarContaPagar();
	}

	private void amortizarOuBaixarContaPagar() {
		final ContaPagar contaPagar = super.dominio.getContaPagar();
		final Set<MovimentacaoContaPagar> movimentacoes = ((RepositorioMovimentacaoContaPagar) super.repositorio)
				.findByContaPagar(contaPagar);
		contaPagar.setMovimentacoes(movimentacoes);
		if (contaPagar.hasSaldoDevedor()) {
			if (contaPagar.hasMontantePago()
					&& !ContaPagar.Situacao.AMORTIZADO.equals(contaPagar.getSituacao())) {
				contaPagar.setSituacao(ContaPagar.Situacao.AMORTIZADO);
				repositorioContaPagar.saveAndFlush(contaPagar);
			}
		} else {
			if (!ContaPagar.Situacao.BAIXADO.equals(contaPagar.getSituacao())) {
				contaPagar.setSituacao(ContaPagar.Situacao.BAIXADO);
				repositorioContaPagar.saveAndFlush(contaPagar);
			}
		}
	}
}
