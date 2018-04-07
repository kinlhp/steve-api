package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.PermissaoCredencial;

public abstract class ValidacaoPermissaoCredencial
		extends ValidavelAbstrato<PermissaoCredencial> {

	private static final long serialVersionUID = 1808229701719886551L;

	protected void validarPermissao() {
		if (super.dominio.getPermissao() != null) {
			if (Permissao.Descricao.SISTEMA.equals(super.dominio.getPermissao().getDescricao())) {
				// TODO: 4/6/18 implementar internacionalizacao
				super.erros.rejectValue("permissao", "permissao.invalid", "Atributo \"permissao\" inválido: Permissão deve ser padrão ou administrador");
			}
		}
	}
}
