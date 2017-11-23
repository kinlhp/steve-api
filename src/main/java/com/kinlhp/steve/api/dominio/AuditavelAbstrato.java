package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;

@EntityListeners(value = {AuditingEntityListener.class})
@Getter
@MappedSuperclass
@Setter
public abstract class AuditavelAbstrato<U extends Serializable, PK extends Serializable>
		extends PersistivelAbstrato<PK> implements Auditavel<U, PK> {

	private static final long serialVersionUID = -3896845469739231200L;

	@Column(name = "data_criacao", updatable = false)
	@CreatedDate
	@JsonFormat(
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			shape = JsonFormat.Shape.STRING
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@NotNull
	private ZonedDateTime dataCriacao;

	@Column(name = "data_ultima_alteracao")
	@JsonFormat(
			pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ",
			shape = JsonFormat.Shape.STRING
	)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@LastModifiedDate
	private ZonedDateTime dataUltimaAlteracao;

	@CreatedBy
	@JoinColumn(name = "usuario_criacao", updatable = false)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@ManyToOne
	@NotNull
	@Valid
	private U usuarioCriacao;

	@JoinColumn(name = "usuario_ultima_alteracao")
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@LastModifiedBy
	@ManyToOne
	@Valid
	private U usuarioUltimaAlteracao;

	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	@Min(value = 0)
	@NotNull
	@Version
	private Integer versao;
}
