package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.temporal.Temporal;

@Getter
@MappedSuperclass
@Setter
public abstract class AuditavelAbstrato<ID extends Serializable, WHEN extends Temporal, WHO extends Serializable>
		extends PersistivelAbstrato<ID> implements Auditavel<ID, WHEN, WHO> {

	private static final long serialVersionUID = 5957460097814132836L;

	@Column(name = "data_criacao", updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@NotNull
	private WHEN dataCriacao;

	@Column(name = "data_ultima_alteracao")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private WHEN dataUltimaAlteracao;

	@JoinColumn(name = "usuario_criacao", updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToOne
	@NotNull
	@Valid
	private WHO usuarioCriacao;

	@JoinColumn(name = "usuario_ultima_alteracao")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToOne
	@Valid
	private WHO usuarioUltimaAlteracao;
}
