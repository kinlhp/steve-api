package com.kinlhp.steve.api.seguranca.token;

import com.kinlhp.steve.api.seguranca.oauth2.Concessao;
import org.apache.catalina.util.ParameterMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpointHandlerMapping;
import org.springframework.stereotype.Component;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.stream.Stream;

@Component
@Order(value = Ordered.HIGHEST_PRECEDENCE)
public class PreProcessadorCookieRefreshToken implements Filter, Serializable {

	private static final long serialVersionUID = -888550624054711438L;
	private final FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping;

	@Autowired
	public PreProcessadorCookieRefreshToken(FrameworkEndpointHandlerMapping frameworkEndpointHandlerMapping) {
		this.frameworkEndpointHandlerMapping = frameworkEndpointHandlerMapping;
	}

	@Override
	public void init(FilterConfig filterConfig) {
		/*
		 */
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
	                     FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest requisicao = (HttpServletRequest) request;
		if (frameworkEndpointHandlerMapping.getPath("/oauth/token").equals(requisicao.getRequestURI())) {
			String concessao = requisicao.getParameter(OAuth2Utils.GRANT_TYPE);
			if (HttpMethod.DELETE.name().equals(requisicao.getMethod())
					|| Concessao.REVOKE_TOKEN.getDescricao().equals(concessao)) {
				HttpServletResponse resposta = (HttpServletResponse) response;
				revogarRefreshToken(resposta);
				return;
			} else if (Concessao.REFRESH_TOKEN.getDescricao().equals(concessao)
					&& requisicao.getCookies() != null) {
				request = escreverRefreshTokenNaRequisicao(requisicao);
			}
		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		/*
		 */
	}

	/**
	 * Le o {@link OAuth2AccessToken#REFRESH_TOKEN} do cookie seguro (http) e
	 * o escreve como parâmetro da requisição
	 */
	private ServletRequest escreverRefreshTokenNaRequisicao(HttpServletRequest requisicao) {
		Cookie cookieRefreshToken = Stream.of(requisicao.getCookies())
				.filter(p -> OAuth2AccessToken.REFRESH_TOKEN.equals(p.getName()))
				.findFirst()
				.orElseThrow(() -> {
					OAuth2Exception e = new InvalidTokenException("Refresh token was not recognised");
					e.addAdditionalInformation(OAuth2Exception.ERROR, OAuth2Exception.INVALID_TOKEN);
					e.addAdditionalInformation(OAuth2Exception.DESCRIPTION, e.getMessage());
					return e;
				});
		return new CookieRefreshTokenRequestWrapper(cookieRefreshToken, requisicao);
	}

	private void revogarRefreshToken(HttpServletResponse resposta) {
		Cookie cookieRefreshToken =
				new Cookie(OAuth2AccessToken.REFRESH_TOKEN, null);
		cookieRefreshToken.setHttpOnly(true);
		cookieRefreshToken.setSecure(false);// TODO: 11/28/17 tornar seguro em produção
		cookieRefreshToken
				.setPath(frameworkEndpointHandlerMapping.getPath("/oauth/token"));
		cookieRefreshToken.setMaxAge(0);
		resposta.addCookie(cookieRefreshToken);
		resposta.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private static class CookieRefreshTokenRequestWrapper
			extends HttpServletRequestWrapper {

		private final Cookie cookieRefreshToken;

		CookieRefreshTokenRequestWrapper(Cookie cookieRefreshToken,
		                                 HttpServletRequest request) {
			super(request);
			this.cookieRefreshToken = cookieRefreshToken;
		}

		@Override
		public Map<String, String[]> getParameterMap() {
			ParameterMap<String, String[]> parametros =
					new ParameterMap<>(getRequest().getParameterMap());
			parametros
					.put(OAuth2AccessToken.REFRESH_TOKEN, new String[]{this.cookieRefreshToken.getValue()});
			parametros.setLocked(true);
			return parametros;
		}
	}
}
