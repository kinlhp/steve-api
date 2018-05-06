package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kinlhp.steve.api.servico.validacao.alteracao.antes.ValidacaoAlteracaoMovimentacaoContaPagar;
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

@Entity(name = "movimentacao_conta_pagar")
@Getter
@Setter
public class MovimentacaoContaPagar
		extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 1438291078499710175L;

	@Column(name = "base_calculo", updatable = false)
	@Min(value = 0)
	@NotNull
	private BigDecimal baseCalculo = BigDecimal.ZERO;

	@JoinColumn(name = "condicao_pagamento", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private CondicaoPagamento condicaoPagamento;

	@JoinColumn(name = "conta_pagar", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private ContaPagar contaPagar;

	@Column(name = "desconto_concedido", updatable = false)
	@Min(value = 0)
	@NotNull
	private BigDecimal descontoConcedido = BigDecimal.ZERO;

	@JsonDeserialize(
			using = ValidacaoAlteracaoMovimentacaoContaPagar
					.ValidacaoAlteracaoEstornado.class
	)
	private boolean estornado;

	@Column(name = "juro_aplicado", updatable = false)
	@Min(value = 0)
	@NotNull
	private BigDecimal juroAplicado = BigDecimal.ZERO;

	@Size(max = 256)
	private String observacao;

	@Column(name = "saldo_devedor", updatable = false)
	@Min(value = 0)
	@NotNull
	private BigDecimal saldoDevedor = BigDecimal.ZERO;

	@Column(name = "valor_pago", updatable = false)
	@Min(value = 0)
	@NotNull
	private BigDecimal valorPago;
}
