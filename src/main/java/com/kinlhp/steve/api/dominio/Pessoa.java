package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.kinlhp.steve.api.servico.validacao.alteracao.ValidacaoAlteracaoPessoa;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "pessoa")
@Getter
@Setter
public class Pessoa extends AuditavelAbstrato<Credencial, BigInteger> {

	private static final long serialVersionUID = -2375250768067185736L;

	@Column(name = "abertura_nascimento")
	private LocalDate aberturaNascimento;

	@Column(name = "cnpj_cpf")
	@NotNull
	@Size(min = 11, max = 14)
	private String cnpjCpf;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "pessoa"
	)
	@Valid
	private Set<Email> emails;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "pessoa"
	)
	@Valid
	private Set<Endereco> enderecos;

	@Column(name = "fantasia_sobrenome")
	@Size(max = 64)
	private String fantasiaSobrenome;

	@Column(name = "ie_rg")
	@NotNull
	@Size(min = 1, max = 16)
	private String ieRg;

	@Column(name = "nome_razao")
	@NotNull
	@Size(min = 1, max = 128)
	private String nomeRazao;

	@Column(name = "perfil_cliente")
	private boolean perfilCliente = true;

	@Column(name = "perfil_fornecedor")
	private boolean perfilFornecedor;

	@Column(name = "perfil_transportador")
	private boolean perfilTransportador;

	@Column(name = "perfil_usuario")
	@JsonDeserialize(
			using = ValidacaoAlteracaoPessoa
					.ValidacaoAlteracaoPerfilUsuario.class
	)
	private boolean perfilUsuario;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Situacao situacao = Situacao.ATIVO;

	@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
	@OneToMany(
			cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
			fetch = FetchType.EAGER,
			mappedBy = "pessoa"
	)
	@Valid
	private Set<Telefone> telefones;

	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Tipo tipo;

	@AllArgsConstructor
	@Getter
	public enum Situacao {
		ATIVO("Ativo"),
		INATIVO("Inativo");

		private final String descricao;
	}

	@AllArgsConstructor
	@Getter
	public enum Tipo {
		FISICA("Física"),
		JURIDICA("Jurídica"),
		SISTEMA("Sistema");

		private final String descricao;
	}
}
