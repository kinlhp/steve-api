package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@EqualsAndHashCode(of = {"id"})
@Getter
@MappedSuperclass
@NoArgsConstructor
@Setter
public abstract class PersistivelAbstrato<PK extends Serializable>
		implements Persistivel<PK> {

	private static final long serialVersionUID = 1487649642760222715L;

	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private PK id;

	public PersistivelAbstrato(PK id) {
		this.id = id;
	}
}
