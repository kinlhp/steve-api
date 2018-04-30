package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kinlhp.steve.api.servico.validacao.alteracao.ValidacaoAlteracaoOrdem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Set;

@Entity(name = "ordem")
@Getter
@Setter
public class Ordem extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 7210615534618372587L;

	@JoinColumn(name = "cliente")
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa cliente;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "ordem"
	)
	@Valid
	private Set<ContaReceber> contasReceber;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "ordem"
	)
	@Valid
	private Set<ItemOrdemServico> itens;

	@Size(max = 256)
	private String observacao;

	@Enumerated(value = EnumType.STRING)
	@JsonDeserialize(using = ValidacaoAlteracaoOrdem.ValidacaoAlteracaoSituacao.class)
	@NotNull
	private Situacao situacao = Situacao.ABERTO;

	@Enumerated(value = EnumType.STRING)
	@JsonDeserialize(using = ValidacaoAlteracaoOrdem.ValidacaoAlteracaoTipo.class)
	@NotNull
	private Tipo tipo = Tipo.ORDEM_SERVICO;

	@AllArgsConstructor
	@Getter
	public enum Situacao {
		ABERTO("Aberto"),
		CANCELADO("Cancelado"),
		FINALIZADO("Finalizado"),
		GERADO("Gerado");

		private final String descricao;
	}

	@AllArgsConstructor
	@Getter
	public enum Tipo {
		ORCAMENTO("Orçamento"),
		ORDEM_SERVICO("Ordem de Serviço");

		private final String descricao;
	}
}
