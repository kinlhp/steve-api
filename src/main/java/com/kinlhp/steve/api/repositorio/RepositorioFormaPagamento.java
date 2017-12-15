package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.FormaPagamento;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "formaspagamento",
		itemResourceRel = "formapagamento",
		path = "formaspagamento"
)
public interface RepositorioFormaPagamento
		extends RepositorioAuditavel<FormaPagamento, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends FormaPagamento> S save(S formaPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends FormaPagamento> List<S> save(Iterable<S> formasPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR')")
	<S extends FormaPagamento> S saveAndFlush(S formaPagamento);
}
