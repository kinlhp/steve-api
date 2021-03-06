package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.repositorio.RepositorioCredencial;
import com.kinlhp.steve.api.repositorio.RepositorioPermissao;
import com.kinlhp.steve.api.repositorio.RepositorioPermissaoCredencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoCredencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeSaveCredencial")
public class ValidacaoAlteracaoCredencial extends ValidacaoCredencial {

	private static final long serialVersionUID = 7533034554287990714L;

	public ValidacaoAlteracaoCredencial(@Autowired RepositorioCredencial repositorio,
	                                    @Autowired RepositorioPermissao repositorioPermissao,
	                                    @Autowired RepositorioPermissaoCredencial repositorioPermissaoCredencial) {
		super(repositorio, repositorioPermissao, repositorioPermissaoCredencial);
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
