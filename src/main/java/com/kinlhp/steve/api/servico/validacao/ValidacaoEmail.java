package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Email;
import com.kinlhp.steve.api.dominio.Permissao;

public abstract class ValidacaoEmail extends ValidavelAbstrato<Email> {

	private static final long serialVersionUID = 1405775721033460860L;

	/**
	 * {@link com.kinlhp.steve.api.dominio.Credencial} sem {@link com.kinlhp.steve.api.dominio.Permissao} de {@link com.kinlhp.steve.api.dominio.Permissao.Descricao#ADMINISTRADOR}
	 * não pode incluir, ou alterar, {@link Email} em {@link com.kinlhp.steve.api.dominio.Pessoa} com perfil de usuário
	 */
	protected void validarPessoa() {
		if (super.dominio.getPessoa() != null
				&& super.dominio.getPessoa().isPerfilUsuario()) {
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR);
		}
	}
}
