package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Auditavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoRepositoryBean
public interface RepositorioAuditavel<T extends Auditavel<U, PK>, U extends Serializable, PK extends Serializable>
		extends RepositorioPersistivel<T, PK> {

	@RestResource(path = "por-criacao", rel = "por-criacao")
	Page<T> findByDataCriacao(
			@Param(value = "data") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") final ZonedDateTime data,
			final Pageable pageable
	);

	@RestResource(path = "por-alteracao", rel = "por-alteracao")
	Page<T> findByDataUltimaAlteracao(
			@Param(value = "data") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") final ZonedDateTime data,
			final Pageable pageable
	);

	@RestResource(path = "por-criador", rel = "por-criador")
	Page<T> findByUsuarioCriacao(@Param(value = "usuario") final U usuario,
	                             final Pageable pageable);

	@RestResource(path = "por-alterador", rel = "por-alterador")
	Page<T> findByUsuarioUltimaAlteracao(@Param(value = "usuario") final U usuario,
	                                     final Pageable pageable);

	@RestResource(path = "por-versao", rel = "por-versao")
	Page<T> findByVersao(@Param(value = "versao") final Integer versao,
	                     final Pageable pageable);
}
