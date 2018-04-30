package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Telefone;

public abstract class ValidacaoTelefone extends ValidavelAbstrato<Telefone> {

	private static final long serialVersionUID = -6175693513221932305L;

	/**
	 * {@link com.kinlhp.steve.api.dominio.Credencial} sem {@link com.kinlhp.steve.api.dominio.Permissao} de {@link com.kinlhp.steve.api.dominio.Permissao.Descricao#ADMINISTRADOR}
	 * não pode incluir, ou alterar, {@link Telefone} em {@link com.kinlhp.steve.api.dominio.Pessoa} com perfil de usuário
	 */
	protected void validarPessoa() {
		if (super.dominio.getPessoa() != null
				&& super.dominio.getPessoa().isPerfilUsuario()) {
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR);
		}
	}
}
