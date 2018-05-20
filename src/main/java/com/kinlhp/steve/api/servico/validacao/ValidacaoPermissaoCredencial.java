package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.PermissaoCredencial;
import com.kinlhp.steve.api.repositorio.RepositorioPermissaoCredencial;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoPermissaoCredencial
		extends ValidavelAbstrato<PermissaoCredencial, BigInteger> {

	private static final long serialVersionUID = -4816295481645571482L;

	public ValidacaoPermissaoCredencial(@Autowired RepositorioPermissaoCredencial repositorio) {
		super(repositorio);
	}

	protected void validarPermissao() {
		if (super.dominio.getPermissao() != null) {
			if (Permissao.Descricao.SISTEMA.equals(super.dominio.getPermissao().getDescricao())) {
				// TODO: 4/6/18 implementar internacionalização
				super.erros.rejectValue("permissao", "permissao.invalid", "Atributo \"permissao\" inválido: Permissão deve ser padrão ou administrador");
			}
		}
	}
}
