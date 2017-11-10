package com.kinlhp.steve.api.dominio;

import java.io.Serializable;

public interface Persistivel<ID extends Serializable> extends Serializable {

	ID getId();

	void setId(final ID id);
}
