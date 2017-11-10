package com.kinlhp.steve.api.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
@Setter
public abstract class PersistivelAbstrato<ID extends Serializable>
		implements Persistivel<ID> {

	private static final long serialVersionUID = 1956153352942311452L;

	@Column(updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private ID id;
}
