package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.PermissaoCredencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "permissoescredencial",
		itemResourceRel = "permissaocredencial",
		path = "permissoescredencial"
)
public interface RepositorioPermissaoCredencial
		extends RepositorioAuditavel<PermissaoCredencial, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends PermissaoCredencial> S save(S permissaoCredencial);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends PermissaoCredencial> List<S> save(Iterable<S> permissoesCredencial);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends PermissaoCredencial> S saveAndFlush(S permissaoCredencial);
}
