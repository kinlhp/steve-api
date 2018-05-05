package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kinlhp.steve.api.servico.validacao.alteracao.antes.ValidacaoAlteracaoContaPagar;
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
import java.time.YearMonth;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Set;

@Entity(name = "conta_pagar")
@Getter
@Setter
public class ContaPagar extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -4861470593068507382L;

	@JoinColumn(name = "cedente")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar.ValidacaoAlteracaoCedente.class
	)
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa cedente;

	@Column(name = "data_emissao")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar
					.ValidacaoAlteracaoDataEmissao.class
	)
	private LocalDate dataEmissao;

	@Column(name = "data_vencimento")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar
					.ValidacaoAlteracaoDataVencimento.class
	)
	@NotNull
	private LocalDate dataVencimento;

	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar.ValidacaoAlteracaoFatura.class
	)
	@NotNull
	@Size(min = 1, max = 32)
	private String fatura;

	@Column(name = "mes_referente")
	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar
					.ValidacaoAlteracaoMesReferente.class
	)
	@NotNull
	private YearMonth mesReferente;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "contaPagar"
	)
	@Valid
	private Set<MovimentacaoContaPagar> movimentacoes;

	@Column(name = "numero_parcela", updatable = false)
	@Min(value = 0)
	@NotNull
	private Integer numeroParcela;

	@Size(max = 256)
	private String observacao;

	@Enumerated(value = EnumType.STRING)
//	@JsonDeserialize(
//			using = ValidacaoAlteracaoContaPagar
//					.ValidacaoAlteracaoSituacao.class
//	)
	@NotNull
	private Situacao situacao;

	@JsonDeserialize(
			using = ValidacaoAlteracaoContaPagar.ValidacaoAlteracaoValor.class
	)
	@Min(value = 0)
	@NotNull
	private BigDecimal valor;

	public BigDecimal getMontantePago() {
		return CollectionUtils.isEmpty(movimentacoes)
				? BigDecimal.ZERO
				: movimentacoes.stream().map(MovimentacaoContaPagar::getValorPago).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	public boolean hasSaldoDevedor() {
		if (BigDecimal.ZERO.compareTo(valor) > 0) {
			return false;
		} else if (CollectionUtils.isEmpty(movimentacoes)) {
			return true;
		}
		final MovimentacaoContaPagar movimentacaoMaisRecente = movimentacoes
				.stream()
				.filter(p -> !p.isEstornado())
				.max(Comparator.comparing(MovimentacaoContaPagar::getDataCriacao))
				// TODO: 5/1/18 implementar internacionalizacao
				.orElseThrow(() -> new NoSuchElementException("Não foi possível obter movimentação mais recente de conta a pagar"));
		return BigDecimal.ZERO.compareTo(movimentacaoMaisRecente.getSaldoDevedor()) > 0;
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
