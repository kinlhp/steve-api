package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Servico;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "servicos",
		itemResourceRel = "servico",
		path = "servicos"
)
public interface RepositorioServico
		extends RepositorioAuditavel<Servico, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Servico> S save(S servico);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Servico> List<S> save(Iterable<S> servicos);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends Servico> S saveAndFlush(S servico);
}
