package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Persistivel;
import com.kinlhp.steve.api.repositorio.RepositorioPersistivel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

import java.io.Serializable;
import java.util.Optional;

public abstract class ValidavelAbstrato<T extends Persistivel<PK>, PK extends Serializable>
		implements Validavel<T> {

	private static final long serialVersionUID = 2646957991982769479L;
	private final MessageSourceAccessor mensagem = SpringSecurityMessageSource
			.getAccessor();
	private final RepositorioPersistivel<T, PK> repositorio;
	protected T dominio;
	protected Errors erros;

	public ValidavelAbstrato(@Autowired RepositorioPersistivel<T, PK> repositorio) {
		this.repositorio = repositorio;
	}

	protected Optional<T> verificarExistencia(PK id) {
		return Optional.ofNullable(repositorio.findOne(id));
	}

	@Override
	public void verificarPermissao(Permissao.Descricao permissao)
			throws AccessDeniedException {
		final Authentication autenticacao = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean temPermissao = autenticacao.getAuthorities()
				.contains(new SimpleGrantedAuthority(permissao.name()));
		if (!temPermissao) {
			// TODO: 5/15/18 implementar internacionalização
			throw new OAuth2AccessDeniedException(mensagem.getMessage(
					"AbstractAccessDecisionManager.accessDenied",
					"Access is denied"));
		}
	}

	@Override
	public void verificarPermissao(Permissao.Descricao permissao,
	                               String motivoAcessoNegado)
			throws AccessDeniedException {
		final Authentication autenticacao = SecurityContextHolder.getContext()
				.getAuthentication();
		boolean temPermissao = autenticacao.getAuthorities()
				.contains(new SimpleGrantedAuthority(permissao.name()));
		if (!temPermissao) {
			// TODO: 5/15/18 implementar internacionalização
			throw new OAuth2AccessDeniedException(mensagem.getMessage(
					"AbstractAccessDecisionManager.accessDenied",
					"Access is denied")
					+ ": "
					+ (StringUtils.isEmpty(motivoAcessoNegado) ? "" : motivoAcessoNegado));
		}
	}
}
