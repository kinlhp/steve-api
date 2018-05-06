package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

@Component(value = "afterSaveMovimentacaoContaPagar")
public class AjusteAlteracaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = -7662785008941618110L;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RepositorioContaPagar repositorioContaPagar;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaPagar) object;
		super.erros = errors;

		// TODO: 5/5/18 implementar design pattern que resolva essa má prática
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
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioContaPagar.saveAndFlush(contaPagar);
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
