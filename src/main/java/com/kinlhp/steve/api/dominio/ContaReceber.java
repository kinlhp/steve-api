package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kinlhp.steve.api.servico.validacao.alteracao.antes.ValidacaoAlteracaoContaReceber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;

@Entity(name = "conta_receber")
@Getter
@Setter
public class ContaReceber extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -7115621384931195115L;

	@JoinColumn(name = "condicao_pagamento")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaReceber
					.ValidacaoAlteracaoCondicaoPagamento.class
	)
	@ManyToOne
	@NotNull
	@Valid
	private CondicaoPagamento condicaoPagamento;

	@Column(name = "data_vencimento")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaReceber
					.ValidacaoAlteracaoDataVencimento.class
	)
	private LocalDate dataVencimento;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "contaReceber"
	)
	@Valid
	private Set<MovimentacaoContaReceber> movimentacoes;

	@Column(name = "numero_parcela", updatable = false)
	@Min(value = 0)
	@NotNull
	private Integer numeroParcela = 0;

	@Size(max = 256)
	private String observacao;

	@JoinColumn(name = "ordem", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private Ordem ordem;

	@JoinColumn(name = "sacado")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaReceber
					.ValidacaoAlteracaoSacado.class
	)
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa sacado;

	@Enumerated(value = EnumType.STRING)
//	@JsonDeserialize(
//			using = ValidacaoAlteracaoContaReceber
//					.ValidacaoAlteracaoSituacao.class
//	)
	@NotNull
	private Situacao situacao = Situacao.ABERTO;

	@JsonDeserialize(
			using = ValidacaoAlteracaoContaReceber
					.ValidacaoAlteracaoValor.class
	)
	@Min(value = 0)
	@NotNull
	private BigDecimal valor;

	public BigDecimal getMontantePago() {
		return CollectionUtils.isEmpty(movimentacoes)
				? BigDecimal.ZERO
				: movimentacoes.stream().map(MovimentacaoContaReceber::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean hasSaldoDevedor() {
		if (BigDecimal.ZERO.compareTo(valor) > 0) {
			return false;
		} else if (CollectionUtils.isEmpty(movimentacoes)) {
			return true;
		}
		final MovimentacaoContaReceber movimentacaoMaisRecente = movimentacoes
				.stream()
				.filter(p -> !p.isEstornado())
				.max(Comparator.comparing(MovimentacaoContaReceber::getDataCriacao))
				// TODO: 5/1/18 implementar internacionalizacao
				.orElseThrow(() -> new NoSuchElementException("Não foi possível obter movimentação mais recente de conta a receber"));
		return BigDecimal.ZERO.compareTo(movimentacaoMaisRecente.getSaldoDevedor()) < 0;
	}

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
