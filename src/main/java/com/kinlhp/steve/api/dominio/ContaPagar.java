package com.kinlhp.steve.api.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;

@Entity(name = "conta_pagar")
@Getter
@Setter
public class ContaPagar
		extends AuditavelAbstrato<BigInteger, ZonedDateTime, Credencial> {

	private static final long serialVersionUID = -2883253148931159796L;

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

	@Column(name = "montante_pago")
	@Min(value = 0)
	@NotNull
	private BigDecimal montantePago;

	@Column(name = "numero_parcela")
	@Min(value = 0)
	@NotNull
	private Integer numeroParcela;

	@Size(max = 256)
	private String observacao;

	@Column(name = "saldo_devedor")
	@Min(value = 0)
	@NotNull
	private BigDecimal saldoDevedor;

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
