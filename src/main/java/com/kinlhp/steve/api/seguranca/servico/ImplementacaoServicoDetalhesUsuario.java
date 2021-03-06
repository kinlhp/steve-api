package com.kinlhp.steve.api.seguranca.servico;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.repositorio.RepositorioCredencial;
import com.kinlhp.steve.api.seguranca.token.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedUserException;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ImplementacaoServicoDetalhesUsuario
		implements Serializable, UserDetailsService {

	private static final long serialVersionUID = -7740754164189493498L;
	private final RepositorioCredencial repositorioCredencial;

	public ImplementacaoServicoDetalhesUsuario(@Autowired RepositorioCredencial repositorioCredencial) {
		this.repositorioCredencial = repositorioCredencial;
	}

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Credencial credencial = repositorioCredencial.findByUsuario(username)
				.orElseThrow(() -> {
					// TODO: 5/18/18 remover refresh token do cookie seguro
					MessageSourceAccessor message = SpringSecurityMessageSource
							.getAccessor();
					return new UnauthorizedUserException(message.getMessage(
							"DigestAuthenticationFilter.usernameNotFound",
							new Object[]{username}));
				});
		if (!Credencial.Situacao.ATIVO.equals(credencial.getSituacao())) {
			MessageSourceAccessor message = SpringSecurityMessageSource
					.getAccessor();
			throw new UnauthorizedUserException(message.getMessage("AccountStatusUserDetailsChecker.disabled"));
		}
		return new DetalhesUsuario<Credencial>(credencial, concederAutoridades(credencial));
	}

	private Collection<? extends GrantedAuthority> concederAutoridades(Credencial credencial) {
		return credencial.getPermissoes()
				.stream()
				.map(p -> new SimpleGrantedAuthority(p.getPermissao().getDescricao().name()))
				.collect(Collectors.toSet());
	}
}
