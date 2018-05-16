package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterCreateMovimentacaoContaReceber")
public class AjusteCriacaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -4088103537903691579L;
	private final RepositorioContaReceber repositorioContaReceber;

	public AjusteCriacaoMovimentacaoContaReceber(@Autowired RepositorioContaReceber repositorioContaReceber) {
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

		baixarContaReceber();
	}

	private void baixarContaReceber() {
		final ContaReceber contaReceber = super.dominio.getContaReceber();
		if (!contaReceber.hasSaldoDevedor()
				&& !ContaReceber.Situacao.BAIXADO.equals(contaReceber.getSituacao())) {
			contaReceber.setSituacao(ContaReceber.Situacao.BAIXADO);
			repositorioContaReceber.saveAndFlush(contaReceber);
		}
	}
}
