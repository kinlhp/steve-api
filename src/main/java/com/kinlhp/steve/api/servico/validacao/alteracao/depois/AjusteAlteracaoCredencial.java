package com.kinlhp.steve.api.servico.validacao.alteracao.depois;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.repositorio.RepositorioPermissao;
import com.kinlhp.steve.api.repositorio.RepositorioPermissaoCredencial;
import com.kinlhp.steve.api.servico.validacao.ValidacaoCredencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "afterSaveCredencial")
public class AjusteAlteracaoCredencial extends ValidacaoCredencial {

	private static final long serialVersionUID = 1367861607830060419L;

	public AjusteAlteracaoCredencial(@Autowired RepositorioPermissao repositorioPermissao,
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

		concederOuRevogarPermissaoAdministrador();
		concederOuRevogarPermissaoPadrao();
	}
}
