package com.kinlhp.steve.api.servico.validacao.criacao.depois;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterCreateContaReceber")
public class AjusteCriacaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = 3407505778208851226L;
	private final RepositorioOrdem repositorioOrdem;

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
		if (super.dominio.getOrdem().getContasReceber().size() == super.dominio.getCondicaoPagamento().getQuantidadeParcelas()) {
			super.dominio.getOrdem().setSituacao(Ordem.Situacao.GERADO);
			repositorioOrdem.saveAndFlush(super.dominio.getOrdem());
		}
	}
}
