package com.kinlhp.steve.api.seguranca.oauth2;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor
@Getter
public enum Concessao {
	PASSWORD("password"),
	REFRESH_TOKEN("refresh_token"),
	REVOKE_TOKEN("revoke_token");

	private final String descricao;

	/**
	 * @return Todas concessões disponíveis
	 */
	public static String[] todas() {
		return Stream
				.of(Concessao.values())
				.map(Concessao::getDescricao)
				.toArray(String[]::new);
	}
}
