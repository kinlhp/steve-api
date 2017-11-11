package com.kinlhp.steve.api.dominio;

import java.io.Serializable;
import java.time.ZonedDateTime;

public interface Auditavel<U extends Serializable, PK extends Serializable>
		extends Persistivel<PK> {

	ZonedDateTime getDataCriacao();

	void setDataCriacao(final ZonedDateTime dataCriacao);

	ZonedDateTime getDataUltimaAlteracao();

	void setDataUltimaAlteracao(final ZonedDateTime dataUltimaAlteracao);

	U getUsuarioCriacao();

	void setUsuarioCriacao(final U usuarioCriacao);

	U getUsuarioUltimaAlteracao();

	void setUsuarioUltimaAlteracao(final U usuarioUltimaAlteracao);

	Integer getVersao();

	void setVersao(final Integer versao);
}
