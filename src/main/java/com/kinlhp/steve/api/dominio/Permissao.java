package com.kinlhp.steve.api.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity(name = "permissao")
@Getter
@Setter
public class Permissao extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 1039311506414367680L;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Descricao descricao;

	public enum Descricao {
		ADMINISTRADOR,
		PADRAO,
		SISTEMA
	}
}
