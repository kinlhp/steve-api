package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Auditavel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.RestResource;

import java.io.Serializable;
import java.time.temporal.Temporal;

@NoRepositoryBean
public interface RepositorioAuditavel<T extends Auditavel<ID, WHEN, WHO>, ID extends Serializable, WHEN extends Temporal, WHO extends Serializable>
		extends RepositorioPersistivel<T, ID> {

	@RestResource(rel = "por-criacao")
	Page<T> findByDataCriacao(WHEN data, Pageable pageable);

	@RestResource(rel = "por-alteracao")
	Page<T> findByDataUltimaAlteracao(WHEN data, Pageable pageable);

	@RestResource(rel = "por-criador")
	Page<T> findByUsuarioCriacao(WHO usuario, Pageable pageable);

	@RestResource(rel = "por-alterador")
	Page<T> findByUsuarioUltimaAlteracao(WHO usuario, Pageable pageable);
}
