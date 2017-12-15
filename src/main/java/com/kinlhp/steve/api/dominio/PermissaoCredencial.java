package com.kinlhp.steve.api.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;

@Entity(name = "permissao_credencial")
@Getter
@Setter
public class PermissaoCredencial
		extends AuditavelAbstrato<Credencial, BigInteger> {

	@JoinColumn(name = "credencial", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private Credencial credencial;

	@JoinColumn(name = "permissao")
	@ManyToOne
	@NotNull
	@Valid
	private Permissao permissao;
}
