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
public class MovimentacaoContaReceber
		extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -7154087627110463782L;

	@Column(name = "base_calculo")
	@Min(value = 0)
	@NotNull
	private BigDecimal baseCalculo = BigDecimal.ZERO;

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

	private boolean estornado;

	@Column(name = "juro_aplicado")
	@Min(value = 0)
	@NotNull
	private BigDecimal juroAplicado = BigDecimal.ZERO;

	@Size(max = 256)
	private String observacao;

	@Column(name = "saldo_devedor")
	@Min(value = 0)
	@NotNull
	private BigDecimal saldoDevedor = BigDecimal.ZERO;

	@Column(name = "valor_pago")
	@Min(value = 0)
	@NotNull
	private BigDecimal valorPago = BigDecimal.ZERO;
}
