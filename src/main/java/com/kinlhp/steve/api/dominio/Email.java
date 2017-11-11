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

@Entity(name = "email")
@Getter
@Setter
public class Email extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -9030952428452696718L;

	@Column(name = "endereco_eletronico")
	@NotNull
	@Size(min = 1, max = 64)
	@org.hibernate.validator.constraints.Email
	private String enderecoEletronico;

	@Column(name = "nome_contato")
	@Size(max = 16)
	private String nomeContato;

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
		NFE("NF-e"),
		PESSOAL("Pessoal"),
		PRINCIPAL("Principal"),
		OUTRO("Outro"),
		TRABALHO("Trabalho");

		private final String descricao;
	}
}
