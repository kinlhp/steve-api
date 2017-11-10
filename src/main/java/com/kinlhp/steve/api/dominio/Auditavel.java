package com.kinlhp.steve.api.dominio;

import java.io.Serializable;
import java.time.temporal.Temporal;

public interface Auditavel<ID extends Serializable, WHEN extends Temporal, WHO extends Serializable>
		extends Persistivel<ID> {

	WHEN getDataCriacao();

	void setDataCriacao(final WHEN dataCriacao);

	WHEN getDataUltimaAlteracao();

	void setDataUltimaAlteracao(final WHEN dataUltimaAlteracao);

	WHO getUsuarioCriacao();

	void setUsuarioCriacao(final WHO usuarioCriacao);

	WHO getUsuarioUltimaAlteracao();

	void setUsuarioUltimaAlteracao(final WHO usuarioUltimaAlteracao);
}
