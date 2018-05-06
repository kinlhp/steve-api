package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;

@Component(value = "afterCreateContaReceber")
public class AjusteCriacaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = 2005752748626920491L;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RepositorioOrdem repositorioOrdem;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaReceber) object;
		super.erros = errors;

		// TODO: 5/5/18 implementar design pattern que resolva essa má prática
		gerarOrdem();
	}

	private void gerarOrdem() {
		if (super.dominio.getOrdem().getContasReceber().size() == super.dominio.getCondicaoPagamento().getQuantidadeParcelas()) {
			super.dominio.getOrdem().setSituacao(Ordem.Situacao.GERADO);
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioOrdem.saveAndFlush(super.dominio.getOrdem());
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
