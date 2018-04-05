package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Persistivel;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public abstract class ValidavelAbstrato<T extends Persistivel>
		implements Validavel {

	private static final long serialVersionUID = -6215580008045780656L;
	private final Authentication autenticacao = SecurityContextHolder
			.getContext().getAuthentication();
	private final MessageSourceAccessor mensagem = SpringSecurityMessageSource
			.getAccessor();
	protected T dominio;
	protected Errors erros;

	@Override
	public void verificarPermissao(Permissao.Descricao permissao)
			throws AccessDeniedException {
		boolean temPermissao = autenticacao.getAuthorities()
				.contains(new SimpleGrantedAuthority(permissao.name()));
		if (!temPermissao) {
			throw new AccessDeniedException(mensagem.getMessage(
					"JdbcDaoImpl.noAuthority",
					new Object[]{autenticacao.getPrincipal()},
					"Access is denied"));
		}
	}

	@Override
	public void verificarPermissao(Permissao.Descricao permissao,
	                               String motivoAcessoNegado)
			throws AccessDeniedException {
		boolean temPermissao = autenticacao.getAuthorities()
				.contains(new SimpleGrantedAuthority(permissao.name()));
		if (!temPermissao) {
			throw new AccessDeniedException(mensagem.getMessage(
					"JdbcDaoImpl.noAuthority",
					new Object[]{autenticacao.getPrincipal()},
					"Access is denied")
					+ ": "
					+ (StringUtils.isEmpty(motivoAcessoNegado) ? "" : motivoAcessoNegado));
		}
	}
}
