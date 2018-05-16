package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.repositorio.RepositorioPermissao;
import com.kinlhp.steve.api.repositorio.RepositorioPermissaoCredencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoCredencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreateCredencial")
public class ValidacaoCriacaoCredencial extends ValidacaoCredencial {

	private static final long serialVersionUID = 5212109662678779589L;

	public ValidacaoCriacaoCredencial(@Autowired RepositorioPermissao repositorioPermissao,
	                                  @Autowired RepositorioPermissaoCredencial repositorioPermissaoCredencial) {
		super(repositorioPermissao, repositorioPermissaoCredencial);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Credencial.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Credencial) object;
		super.erros = errors;

		validarFuncionario();
	}
}
