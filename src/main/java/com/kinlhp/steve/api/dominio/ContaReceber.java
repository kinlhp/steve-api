package com.kinlhp.steve.api.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Entity(name = "conta_receber")
@Getter
@Setter
public class ContaReceber
		extends AuditavelAbstrato<BigInteger, ZonedDateTime, Credencial> {

	private static final long serialVersionUID = -3273118562626360971L;

	@JoinColumn(name = "condicao_pagamento")
	@ManyToOne
	@NotNull
	@Valid
	private CondicaoPagamento condicaoPagamento;

	@Column(name = "data_vencimento")
	private LocalDate dataVencimento;

	@Column(name = "montante_pago")
	@Min(value = 0)
	@NotNull
	private BigDecimal montantePago = BigDecimal.ZERO;

	@Column(name = "numero_parcela")
	@Min(value = 0)
	@NotNull
	private Integer numeroParcela = 0;

	@Size(max = 256)
	private String observacao;

	@JoinColumn(name = "ordem")
	@ManyToOne
	@NotNull
	@Valid
	private Ordem ordem;

	@JoinColumn(name = "sacado")
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa sacado;

	@Column(name = "saldo_devedor")
	@Min(value = 0)
	@NotNull
	private BigDecimal saldoDevedor;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Situacao situacao = Situacao.ABERTO;

	@Min(value = 0)
	@NotNull
	private BigDecimal valor;

	@AllArgsConstructor
	@Getter
	public enum Situacao {
		ABERTO("Aberto"),
		AMORTIZADO("Amortizado"),
		BAIXADO("Baixado"),
		CANCELADO("Cancelado");

		private final String descricao;
	}
}
