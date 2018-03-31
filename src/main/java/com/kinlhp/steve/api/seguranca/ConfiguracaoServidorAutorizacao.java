package com.kinlhp.steve.api.seguranca;

import com.kinlhp.steve.api.seguranca.oauth2.Concessao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.Serializable;

@Configuration
@EnableAuthorizationServer
public class ConfiguracaoServidorAutorizacao
		extends AuthorizationServerConfigurerAdapter implements Serializable {

	private static final long serialVersionUID = -8558444440339214611L;
	private final AuthenticationManager authenticationManager;

	@Value(value = "${spring.data.rest.base-path}")
	private String basePath;

	@Autowired
	public ConfiguracaoServidorAutorizacao(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public void configure(ClientDetailsServiceConfigurer clients)
			throws Exception {
		// TODO: 12/1/17 alterar para JDBC
		clients.inMemory()
				.withClient("aplicativo").secret("@pl1c@t1v0").scopes("escrita", "leitura")
				.authorizedGrantTypes(Concessao.todas())
				.accessTokenValiditySeconds(20)
				.refreshTokenValiditySeconds(3_600 * 24)
				.and()
				.withClient("navegador").secret("n@ve9@d0r").scopes("escrita", "leitura")
				.authorizedGrantTypes(Concessao.todas())
				.accessTokenValiditySeconds(20)
				.refreshTokenValiditySeconds(3_600 * 24);

	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
		endpoints
				.pathMapping("/oauth/authorize", basePath + "/oauth/authorize")
				.pathMapping("/oauth/check_token", basePath + "/oauth/check_token")
				.pathMapping("/oauth/confirm_access", basePath + "/oauth/confirm_access")
				.pathMapping("/oauth/error", basePath + "/oauth/error")
				.pathMapping("/oauth/token", basePath + "/oauth/token")
				.allowedTokenEndpointRequestMethods(HttpMethod.DELETE, HttpMethod.GET, HttpMethod.POST)
				.accessTokenConverter(accessTokenConverter())
				.tokenStore(tokenStore())
//				.reuseRefreshTokens(false)
				.authenticationManager(authenticationManager);
	}

	@Bean
	public AccessTokenConverter accessTokenConverter() {
		JwtAccessTokenConverter jwtAccessTokenConverter =
				new JwtAccessTokenConverter();
		// TODO: 3/27/18 remover hard coded
		jwtAccessTokenConverter.setSigningKey("steve");
		return jwtAccessTokenConverter;
	}

	@Bean
	public TokenStore tokenStore() {
		return new JwtTokenStore((JwtAccessTokenConverter) accessTokenConverter());
	}
}
