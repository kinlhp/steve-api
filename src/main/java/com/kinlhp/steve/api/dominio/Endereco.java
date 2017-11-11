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
import java.math.BigInteger;

@Entity(name = "endereco")
@Getter
@Setter
public class Endereco extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 5940836452430044612L;

	@NotNull
	@Size(min = 1, max = 64)
	private String bairro;

	@NotNull
	@Size(min = 8, max = 8)
	private String cep;

	@NotNull
	@Size(min = 1, max = 64)
	private String cidade;

	@Size(max = 64)
	private String complemento;

	@Column(name = "complemento_2")
	@Size(max = 64)
	private String complemento2;

	@Min(value = 0)
	private Integer ibge;

	@NotNull
	@Size(min = 1, max = 64)
	private String logradouro;

	@Column(name = "nome_contato")
	@Size(max = 16)
	private String nomeContato;

	@NotNull
	@Size(min = 1, max = 8)
	private String numero = "s.n.º";

	@JoinColumn(name = "pessoa", updatable = false)
	@ManyToOne
	@NotNull
	@Valid
	private Pessoa pessoa;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Tipo tipo = Tipo.PRINCIPAL;

	@JoinColumn(name = "uf")
	@ManyToOne
	@NotNull
	@Valid
	private Uf uf;

	@AllArgsConstructor
	@Getter
	public enum Tipo {
		ENTREGA("Entrega"),
		PESSOAL("Pessoal"),
		PRINCIPAL("Principal"),
		OUTRO("Outro"),
		TRABALHO("Trabalho");

		private final String descricao;
	}
}
