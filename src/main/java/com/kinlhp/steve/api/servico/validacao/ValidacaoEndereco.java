package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Endereco;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioEndereco;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoEndereco
		extends ValidavelAbstrato<Endereco, BigInteger> {

	private static final long serialVersionUID = -5740564410331738861L;

	public ValidacaoEndereco(@Autowired RepositorioEndereco repositorio) {
		super(repositorio);
	}

	/**
	 * {@link com.kinlhp.steve.api.dominio.Credencial} sem {@link com.kinlhp.steve.api.dominio.Permissao} de {@link com.kinlhp.steve.api.dominio.Permissao.Descricao#ADMINISTRADOR}
	 * não pode incluir, ou alterar, {@link Endereco} em {@link com.kinlhp.steve.api.dominio.Pessoa} com perfil de usuário
	 */
	protected void validarPessoa() {
		if (super.dominio.getPessoa() != null
				&& super.dominio.getPessoa().isPerfilUsuario()) {
			// TODO: 5/15/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"pessoa\" inválido: Somente usuário administrador pode definir endereço para pessoa com perfil de usuário");
		}
	}
}
