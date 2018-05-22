package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.Set;

@Component(value = "afterCreateContaReceber")
public class AjusteCriacaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = 8021457081286976392L;
	private final RepositorioOrdem repositorioOrdem;

	@PersistenceContext
	private EntityManager entityManager;

	public AjusteCriacaoContaReceber(@Autowired RepositorioContaReceber repositorio,
	                                 @Autowired RepositorioOrdem repositorioOrdem) {
		super(repositorio);
		this.repositorioOrdem = repositorioOrdem;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaReceber) object;
		super.erros = errors;

		validarSituacaoOrdem();
	}

	private void validarSituacaoOrdem() {
		final Ordem ordem = super.dominio.getOrdem();
		final Set<ContaReceber> contasReceber = ((RepositorioContaReceber) super.repositorio)
				.findByOrdem(ordem);
		if (contasReceber.size() == super.dominio.getCondicaoPagamento().getQuantidadeParcelas()) {
			ordem.setSituacao(Ordem.Situacao.GERADO);
			try {
				entityManager.setFlushMode(FlushModeType.COMMIT);
				repositorioOrdem.saveAndFlush(ordem);
			} finally {
				entityManager.setFlushMode(FlushModeType.AUTO);
			}
		}
	}
}
