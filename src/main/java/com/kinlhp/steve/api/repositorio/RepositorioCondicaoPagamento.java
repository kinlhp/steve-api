package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.CondicaoPagamento;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "condicoesPagamento",
		itemResourceRel = "condicaoPagamento",
		path = "condicoesPagamento"
)
public interface RepositorioCondicaoPagamento
		extends RepositorioAuditavel<CondicaoPagamento, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends CondicaoPagamento> S save(S condicaoPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends CondicaoPagamento> List<S> save(Iterable<S> condicoesPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends CondicaoPagamento> S saveAndFlush(S condicaoPagamento);
}
