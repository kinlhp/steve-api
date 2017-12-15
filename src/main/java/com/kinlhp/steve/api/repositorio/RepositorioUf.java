package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Uf;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "ufs",
		itemResourceRel = "uf",
		path = "ufs"
)
public interface RepositorioUf
		extends RepositorioAuditavel<Uf, Credencial, BigInteger> {

	@Override
	@RestResource(exported = false)
	<S extends Uf> S save(S uf);

	@Override
	@RestResource(exported = false)
	<S extends Uf> List<S> save(Iterable<S> ufs);

	@Override
	@RestResource(exported = false)
	<S extends Uf> S saveAndFlush(S uf);
}
