package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Telefone;
import com.kinlhp.steve.api.repositorio.RepositorioTelefone;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoTelefone
		extends ValidavelAbstrato<Telefone, BigInteger> {

	private static final long serialVersionUID = 6589827688664872444L;

	public ValidacaoTelefone(@Autowired RepositorioTelefone repositorio) {
		super(repositorio);
	}

	/**
	 * {@link com.kinlhp.steve.api.dominio.Credencial} sem {@link com.kinlhp.steve.api.dominio.Permissao} de {@link com.kinlhp.steve.api.dominio.Permissao.Descricao#ADMINISTRADOR}
	 * não pode incluir, ou alterar, {@link Telefone} em {@link com.kinlhp.steve.api.dominio.Pessoa} com perfil de usuário
	 */
	protected void validarPessoa() {
		if (super.dominio.getPessoa() != null
				&& super.dominio.getPessoa().isPerfilUsuario()) {
			// TODO: 5/15/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"pessoa\" inválido: Somente usuário administrador pode definir telefone para pessoa com perfil de usuário");
		}
	}
}
