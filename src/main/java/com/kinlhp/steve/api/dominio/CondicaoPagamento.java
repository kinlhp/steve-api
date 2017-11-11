package com.kinlhp.steve.api.dominio;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity(name = "condicao_pagamento")
@Getter
@Setter
public class CondicaoPagamento
		extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -8076965554856626392L;

	@NotNull
	@Size(min = 1, max = 32)
	private String descricao;

	@JoinColumn(name = "formaPagamento")
	@ManyToOne
	@NotNull
	@Valid
	private FormaPagamento formaPagamento;

	@Column(name = "periodo_entre_parcelas")
	@Min(value = 0)
	@NotNull
	private Integer periodoEntreParcelas = 0;

	@Column(name = "quantidade_parcelas")
	@Min(value = 0)
	@NotNull
	private Integer quantidadeParcelas = 0;
}
