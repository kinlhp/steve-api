package com.kinlhp.steve.api.seguranca.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.security.Principal;
import java.util.Map;

@RestControllerAdvice
public class PosProcessadorRefreshToken
		implements ResponseBodyAdvice<OAuth2AccessToken>, Serializable {

	private static final long serialVersionUID = 5179064071110661273L;
	private final FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping;

	public PosProcessadorRefreshToken(@Autowired FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping) {
		this.frameworkEndpointHandlerMapping = frameworkEndpointHandlerMapping;
	}

	/**
	 * Identifica se a resposta corresponde ao retorno de {@link TokenEndpoint#getAccessToken(Principal, Map)}
	 * ou {@link TokenEndpoint#postAccessToken(Principal, Map)}
	 */
	@Override
	public boolean supports(MethodParameter returnType,
	                        Class<? extends HttpMessageConverter<?>> converterType) {
		return returnType.getMethod().getName().equals("getAccessToken")
				|| returnType.getMethod().getName().equals("postAccessToken");
	}

	@Override
	public OAuth2AccessToken beforeBodyWrite(OAuth2AccessToken body,
	                                         MethodParameter returnType,
	                                         MediaType selectedContentType,
	                                         Class<? extends HttpMessageConverter<?>> selectedConverterType,
	                                         ServerHttpRequest request,
	                                         ServerHttpResponse response) {
		DefaultOAuth2AccessToken corpo = (DefaultOAuth2AccessToken) body;
		HttpServletResponse resposta = ((ServletServerHttpResponse) response)
				.getServletResponse();
		moverRefreshTokenParaCookie(corpo, resposta);
		return body;
	}

	/**
	 * Move o {@link OAuth2AccessToken#REFRESH_TOKEN} do corpo da resposta para cookie seguro (https)
	 */
	private void moverRefreshTokenParaCookie(DefaultOAuth2AccessToken corpo,
	                                         HttpServletResponse resposta) {
		String refreshToken = corpo.getRefreshToken().getValue();
		Cookie cookieRefreshToken =
				new Cookie(OAuth2AccessToken.REFRESH_TOKEN, refreshToken);
		cookieRefreshToken.setHttpOnly(true);
		cookieRefreshToken.setSecure(false);// TODO: 11/28/17 tornar seguro em produção
		cookieRefreshToken
				.setPath(frameworkEndpointHandlerMapping.getPath("/oauth/token"));
		cookieRefreshToken.setMaxAge(3_600 * 24 * 7);
		resposta.addCookie(cookieRefreshToken);
		corpo.setRefreshToken(null);
	}
}
