package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

@Component(value = "afterCreateMovimentacaoContaReceber")
public class AjusteCriacaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -4673283523512463184L;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RepositorioContaReceber repositorioContaReceber;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaReceber) object;
		super.erros = errors;

		// TODO: 5/5/18 implementar design pattern que resolva essa má prática
		baixarContaReceber();
	}

	private void baixarContaReceber() {
		final ContaReceber contaReceber = super.dominio.getContaReceber();
		if (!contaReceber.hasSaldoDevedor()
				&& !ContaReceber.Situacao.BAIXADO.equals(contaReceber.getSituacao())) {
			contaReceber.setSituacao(ContaReceber.Situacao.BAIXADO);
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioContaReceber.saveAndFlush(contaReceber);
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
