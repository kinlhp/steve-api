package com.kinlhp.steve.api.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;

@Entity(name = "uf")
@Getter
@Setter
public class Uf extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = 2433007530703455137L;

	@Min(value = 0)
	@NotNull
	private Integer ibge;

	@NotNull
	@Size(min = 1, max = 19)
	private String nome;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Sigla sigla;

	@AllArgsConstructor
	@Getter
	public enum Sigla {
		AC("Acre"),
		AL("Alagoas"),
		AM("Amazonas"),
		AP("Amapá"),
		BA("Bahia"),
		CE("Ceará"),
		DF("Distrito Federal"),
		ES("Espírito Santo"),
		EX("Exterior"),
		GO("Goiás"),
		MA("Maranhão"),
		MG("Minas Gerais"),
		MS("Mato Grosso do Sul"),
		MT("Mato Grosso"),
		PA("Pará"),
		PB("Paraíba"),
		PE("Pernambuco"),
		PI("Piauí"),
		PR("Paraná"),
		RJ("Rio de Janeiro"),
		RN("Rio Grande do Norte"),
		RO("Rondônia"),
		RR("Roraima"),
		RS("Rio Grande do Sul"),
		SC("Santa Catarina"),
		SE("Sergipe"),
		SP("São Paulo"),
		TO("Tocantins");

		private final String descricao;
	}
}
