package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Set;

@Entity(name = "forma_pagamento")
@Getter
@Setter
public class FormaPagamento extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 2230203299841676971L;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "formaPagamento"
	)
	@Valid
	private Set<CondicaoPagamento> condicoesPagamento;

	@NotNull
	@Size(min = 1, max = 32)
	private String descricao;
}
