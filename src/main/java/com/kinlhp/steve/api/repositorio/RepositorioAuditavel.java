package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Auditavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.Serializable;
import java.time.ZonedDateTime;

@NoRepositoryBean
public interface RepositorioAuditavel<T extends Auditavel<U, PK>, U extends Serializable, PK extends Serializable>
		extends RepositorioPersistivel<T, PK> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "criacao", rel = "criacao")
	Page<T> findByDataCriacao(
			@Param(value = "data") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") final ZonedDateTime data,
			final Pageable pageable
	);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "alteracao", rel = "alteracao")
	Page<T> findByDataUltimaAlteracao(
			@Param(value = "data") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ") final ZonedDateTime data,
			final Pageable pageable
	);

	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('leitura')")
	@RestResource(path = "criador", rel = "criador")
	Page<T> findByUsuarioCriacao(@Param(value = "usuario") final U usuario,
	                             final Pageable pageable);

	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('leitura')")
	@RestResource(path = "alterador", rel = "alterador")
	Page<T> findByUsuarioUltimaAlteracao(@Param(value = "usuario") final U usuario,
	                                     final Pageable pageable);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "versao", rel = "versao")
	Page<T> findByVersao(@Param(value = "versao") final Integer versao,
	                     final Pageable pageable);
}
