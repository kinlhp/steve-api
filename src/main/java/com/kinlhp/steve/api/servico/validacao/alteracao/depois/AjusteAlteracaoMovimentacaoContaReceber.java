package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

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
import java.math.BigDecimal;

@Component(value = "afterSaveMovimentacaoContaReceber")
public class AjusteAlteracaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -1195258900688529279L;

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
		reabrirContaReceber();
	}

	private void reabrirContaReceber() {
		final ContaReceber contaReceber = super.dominio.getContaReceber();
		if (contaReceber.hasSaldoDevedor()) {
			if (BigDecimal.ZERO.compareTo(contaReceber.getMontantePago()) < 0) {
				contaReceber.setSituacao(ContaReceber.Situacao.AMORTIZADO);
			} else {
				contaReceber.setSituacao(ContaReceber.Situacao.ABERTO);
			}
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioContaReceber.saveAndFlush(contaReceber);
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
