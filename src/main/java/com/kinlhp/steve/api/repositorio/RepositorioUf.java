package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Uf;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
		collectionResourceRel = "ufs",
		itemResourceRel = "uf",
		path = "ufs"
)
public interface RepositorioUf
		extends RepositorioAuditavel<Uf, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Uf> S save(S uf);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Uf> List<S> save(Iterable<S> ufs);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Uf> S saveAndFlush(S uf);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "sigla", rel = "sigla")
	Optional<Uf> findBySigla(@Param(value = "sigla") Uf.Sigla sigla);
}
