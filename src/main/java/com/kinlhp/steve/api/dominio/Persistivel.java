package com.kinlhp.steve.api.dominio;

import java.io.Serializable;

public interface Persistivel<PK extends Serializable> extends Serializable {

	PK getId();

	void setId(final PK id);
}
