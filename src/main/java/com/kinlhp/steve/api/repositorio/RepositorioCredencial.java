package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
		collectionResourceRel = "credenciais",
		itemResourceRel = "credencial",
		path = "credenciais"
)
public interface RepositorioCredencial
		extends RepositorioAuditavel<Credencial, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Credencial> S save(S entity);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Credencial> List<S> save(Iterable<S> credenciais);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Credencial> S saveAndFlush(S entity);

	Optional<Credencial> findByUsuario(@Param(value = "usuario") String usuario);
}
