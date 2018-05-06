package com.kinlhp.steve.api.servico.validacao.criacao.depois;

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

@Component(value = "afterCreateMovimentacaoContaPagar")
public class AjusteCriacaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = 7260000879629015835L;

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
		baixarContaPagar();
	}

	private void baixarContaPagar() {
		final ContaPagar contaPagar = super.dominio.getContaPagar();
		if (!contaPagar.hasSaldoDevedor()
				&& !ContaPagar.Situacao.BAIXADO.equals(contaPagar.getSituacao())) {
			contaPagar.setSituacao(ContaPagar.Situacao.BAIXADO);
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioContaPagar.saveAndFlush(contaPagar);
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
