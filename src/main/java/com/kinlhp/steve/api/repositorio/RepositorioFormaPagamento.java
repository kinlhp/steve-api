package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.FormaPagamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;

@RepositoryRestResource(
		collectionResourceRel = "formasPagamento",
		itemResourceRel = "formaPagamento",
		path = "formasPagamento"
)
public interface RepositorioFormaPagamento
		extends RepositorioAuditavel<FormaPagamento, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends FormaPagamento> S save(S formaPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends FormaPagamento> List<S> save(Iterable<S> formasPagamento);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends FormaPagamento> S saveAndFlush(S formaPagamento);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "descricao", rel = "descricao")
	Page<FormaPagamento> findByDescricaoContaining(@Param(value = "descricao") String descricao,
	                                               Pageable pageable);
}
