package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.util.Set;

@Component(value = "afterCreateMovimentacaoContaReceber")
public class AjusteCriacaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = 5119541881059378526L;
	private final RepositorioContaReceber repositorioContaReceber;

	public AjusteCriacaoMovimentacaoContaReceber(@Autowired RepositorioMovimentacaoContaReceber repositorio,
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

		amortizarOuBaixarContaReceber();
	}

	private void amortizarOuBaixarContaReceber() {
		final ContaReceber contaReceber = super.dominio.getContaReceber();
		final Set<MovimentacaoContaReceber> movimentacoes = ((RepositorioMovimentacaoContaReceber) super.repositorio)
				.findByContaReceber(contaReceber);
		contaReceber.setMovimentacoes(movimentacoes);
		if (contaReceber.hasSaldoDevedor()) {
			if (contaReceber.hasMontantePago()
					&& !ContaReceber.Situacao.AMORTIZADO.equals(contaReceber.getSituacao())) {
				contaReceber.setSituacao(ContaReceber.Situacao.AMORTIZADO);
				repositorioContaReceber.saveAndFlush(contaReceber);
			}
		} else {
			if (!ContaReceber.Situacao.BAIXADO.equals(contaReceber.getSituacao())) {
				contaReceber.setSituacao(ContaReceber.Situacao.BAIXADO);
				repositorioContaReceber.saveAndFlush(contaReceber);
			}
		}
	}
}
