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

@Entity(name = "item_ordem_servico")
@Getter
@Setter
public class ItemOrdemServico
		extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -2325486476898097310L;

	@Column(name = "data_finalizacao_prevista")
	private LocalDate dataFinalizacaoPrevista;

	@Size(max = 1024)
	private String descricao;

	@JoinColumn(name = "ordem", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private Ordem ordem;

	@JoinColumn(name = "servico")
	@ManyToOne
	@NotNull
	@Valid
	private Servico servico;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Situacao situacao = Situacao.ABERTO;

	@Column(name = "valor_orcamento")
	@Min(value = 0)
	@NotNull
	private BigDecimal valorOrcamento = BigDecimal.ZERO;

	@Column(name = "valor_servico")
	@Min(value = 0)
	@NotNull
	private BigDecimal valorServico = BigDecimal.ZERO;

	@AllArgsConstructor
	@Getter
	public enum Situacao {
		ABERTO("Aberto"),
		CANCELADO("Cancelado"),
		FINALIZADO("Finalizado");

		private final String descricao;
	}
}
