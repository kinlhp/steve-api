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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.ZonedDateTime;

@Entity(name = "telefone")
@Getter
@Setter
public class Telefone
		extends AuditavelAbstrato<BigInteger, ZonedDateTime, Credencial> {

	private static final long serialVersionUID = 1276974703390176187L;

	@Column(name = "nome_contato")
	@Size(max = 16)
	private String nomeContato;

	@NotNull
	@Size(min = 1, max = 16)
	private String numero;

	@JoinColumn(name = "pessoa", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa pessoa;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Tipo tipo = Tipo.PRINCIPAL;

	@AllArgsConstructor
	@Getter
	public enum Tipo {
		PESSOAL("Pessoal"),
		PRINCIPAL("Principal"),
		OUTRO("Outro"),
		TRABALHO("Trabalho");

		private final String descricao;
	}
}
