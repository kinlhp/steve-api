package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
import java.util.Set;

@Entity(name = "conta_pagar")
@Getter
@Setter
public class ContaPagar extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 4711639240567561818L;

	@JoinColumn(name = "cedente")
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa cedente;

	@Column(name = "data_emissao")
	private LocalDate dataEmissao;

	@Column(name = "data_vencimento")
	@NotNull
	private LocalDate dataVencimento;

	@NotNull
	@Size(min = 1, max = 32)
	private String fatura;

	@Column(name = "mes_referente")
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

	@Column(name = "numero_parcela")
	@Min(value = 0)
	@NotNull
	private Integer numeroParcela;

	@Size(max = 256)
	private String observacao;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Situacao situacao;

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
