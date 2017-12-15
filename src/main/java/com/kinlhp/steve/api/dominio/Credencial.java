package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.util.Set;

@Entity(name = "credencial")
@Getter
@Setter
public class Credencial extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -517389021541870586L;

	@JoinColumn(name = "funcionario")
	@NotNull
	@OneToOne
	@Valid
	private Pessoa funcionario;

	@Column(name = "perfil_administrador")
	private boolean perfilAdministrador;

	@Column(name = "perfil_padrao")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private boolean perfilPadrao = true;

	@Column(name = "perfil_sistema")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private boolean perfilSistema;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "credencial"
	)
	@Valid
	private Set<PermissaoCredencial> permissoes;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@NotNull
	@Size(min = 128, max = 128)
	private String senha;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Situacao situacao = Situacao.ATIVO;

	@NotNull
	@Size(min = 1, max = 16)
	private String usuario;

	@AllArgsConstructor
	@Getter
	public enum Situacao {
		ATIVO("Ativo"),
		INATIVO("Inativo");

		private final String descricao;
	}
}
