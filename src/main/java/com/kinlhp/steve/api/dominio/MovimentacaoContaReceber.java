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
import java.math.BigDecimal;
import java.math.BigInteger;

@Entity(name = "movimentacao_conta_receber")
@Getter
@Setter
public class MovimentacaoContaReceber extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 634408193603801989L;

	@JoinColumn(name = "condicao_pagamento")
	@ManyToOne
	@NotNull
	@Valid
	private CondicaoPagamento condicaoPagamento;

	@JoinColumn(name = "conta_receber")
	@ManyToOne
	@NotNull
	@Valid
	private ContaReceber contaReceber;

	@Column(name = "desconto_concedido")
	@Min(value = 0)
	@NotNull
	private BigDecimal descontoConcedido = BigDecimal.ZERO;

	@Column(name = "juro_aplicado")
	@Min(value = 0)
	@NotNull
	private BigDecimal juroAplicado = BigDecimal.ZERO;

	@Size(max = 256)
	private String observacao;

	@Min(value = 0)
	@NotNull
	private BigDecimal valor;
}
