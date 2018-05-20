package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.PermissaoCredencial;
import com.kinlhp.steve.api.repositorio.RepositorioCredencial;
import com.kinlhp.steve.api.repositorio.RepositorioPermissao;
import com.kinlhp.steve.api.repositorio.RepositorioPermissaoCredencial;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoCredencial
		extends ValidavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -7891063703884672968L;
	private final RepositorioPermissao repositorioPermissao;
	private final RepositorioPermissaoCredencial repositorioPermissaoCredencial;

	public ValidacaoCredencial(@Autowired RepositorioCredencial repositorio,
	                           @Autowired RepositorioPermissao repositorioPermissao,
	                           @Autowired RepositorioPermissaoCredencial repositorioPermissaoCredencial) {
		super(repositorio);
		this.repositorioPermissao = repositorioPermissao;
		this.repositorioPermissaoCredencial = repositorioPermissaoCredencial;
	}

	protected void concederOuRevogarPermissaoAdministrador() {
		if (super.dominio.isPerfilAdministrador()) {
			if (super.dominio.getPermissoes() == null
					|| super.dominio.getPermissoes().stream().noneMatch(p -> Permissao.Descricao.ADMINISTRADOR.equals(p.getPermissao().getDescricao()))) {
				concederPermissaoAdministrador();
			}
		} else if (!super.dominio.isPerfilAdministrador()) {
			if (super.dominio.getPermissoes() != null
					&& super.dominio.getPermissoes().stream().anyMatch(p -> Permissao.Descricao.ADMINISTRADOR.equals(p.getPermissao().getDescricao()))) {
				revogarPermissaoAdministrador();
			}
		}
	}

	protected void concederOuRevogarPermissaoPadrao() {
		if (super.dominio.isPerfilPadrao()) {
			if (super.dominio.getPermissoes() == null
					|| super.dominio.getPermissoes().stream().noneMatch(p -> Permissao.Descricao.PADRAO.equals(p.getPermissao().getDescricao()))) {
				concederPermissaoPadrao();
			}
		} else if (!super.dominio.isPerfilPadrao()) {
			if (super.dominio.getPermissoes() != null
					&& super.dominio.getPermissoes().stream().anyMatch(p -> Permissao.Descricao.PADRAO.equals(p.getPermissao().getDescricao()))) {
				revogarPermissaoPadrao();
			}
		}
	}

	private void concederPermissaoAdministrador() {
		final Permissao permissao = repositorioPermissao
				.findByDescricao(Permissao.Descricao.ADMINISTRADOR)
				.orElse(null);
		final PermissaoCredencial permissaoCredencial =
				new PermissaoCredencial();
		permissaoCredencial.setCredencial(super.dominio);
		permissaoCredencial.setPermissao(permissao);
		repositorioPermissaoCredencial.saveAndFlush(permissaoCredencial);
	}

	private void concederPermissaoPadrao() {
		final Permissao permissao = repositorioPermissao
				.findByDescricao(Permissao.Descricao.PADRAO)
				.orElse(null);
		final PermissaoCredencial permissaoCredencial =
				new PermissaoCredencial();
		permissaoCredencial.setCredencial(super.dominio);
		permissaoCredencial.setPermissao(permissao);
		repositorioPermissaoCredencial.saveAndFlush(permissaoCredencial);
	}

	private void revogarPermissaoAdministrador() {
		final PermissaoCredencial permissaoCredencial = super.dominio
				.getPermissoes()
				.stream()
				.filter(p -> Permissao.Descricao.ADMINISTRADOR.equals(p.getPermissao().getDescricao()))
				.findFirst()
				.orElse(null);
		if (permissaoCredencial != null
				&& permissaoCredencial.getId() != null) {
			super.dominio.getPermissoes().remove(permissaoCredencial);
			repositorioPermissaoCredencial.delete(permissaoCredencial.getId());
		}
	}

	private void revogarPermissaoPadrao() {
		final PermissaoCredencial permissaoCredencial = super.dominio
				.getPermissoes()
				.stream()
				.filter(p -> Permissao.Descricao.PADRAO.equals(p.getPermissao().getDescricao()))
				.findFirst()
				.orElse(null);
		if (permissaoCredencial != null
				&& permissaoCredencial.getId() != null) {
			super.dominio.getPermissoes().remove(permissaoCredencial);
			repositorioPermissaoCredencial.delete(permissaoCredencial.getId());
		}
	}

	protected void validarFuncionario() {
		if (super.dominio.getFuncionario() != null) {
			if (BigInteger.ZERO.compareTo(super.dominio.getFuncionario().getId()) >= 0
					|| !super.dominio.getFuncionario().isPerfilUsuario()) {
				// TODO: 4/4/18 implementar internacionalização
				super.erros.rejectValue("funcionario", "funcionario.invalid", "Atributo \"funcionario\" inválido: Somente pessoa com perfil de usuário pode ter credencial");
			}
		}
	}
}
