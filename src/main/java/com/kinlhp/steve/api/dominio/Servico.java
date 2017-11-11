package com.kinlhp.steve.api.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity(name = "servico")
@Getter
@Setter
public class Servico extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 6278348323018235171L;

	@NotNull
	@Size(min = 1, max = 64)
	private String descricao;
}
