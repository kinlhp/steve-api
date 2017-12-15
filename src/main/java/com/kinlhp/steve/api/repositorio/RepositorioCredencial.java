package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "credenciais",
		itemResourceRel = "credencial",
		path = "credenciais"
)
public interface RepositorioCredencial
		extends RepositorioAuditavel<Credencial, Credencial, BigInteger> {

	@Override
	@RestResource(exported = false)
	<S extends Credencial> List<S> save(Iterable<S> credenciais);
}
