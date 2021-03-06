package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Permissao;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@RepositoryRestResource(
		collectionResourceRel = "permissoes",
		itemResourceRel = "permissao",
		path = "permissoes"
)
public interface RepositorioPermissao
		extends RepositorioAuditavel<Permissao, Credencial, BigInteger> {

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Permissao> S save(S permissao);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Permissao> List<S> save(Iterable<S> permissoes);

	@Override
	@PreAuthorize(value = "hasAuthority('SISTEMA') and #oauth2.hasScope('escrita')")
	@RestResource(exported = false)
	<S extends Permissao> S saveAndFlush(S permissao);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "descricao", rel = "descricao")
	Optional<Permissao> findByDescricao(@Param(value = "descricao") Permissao.Descricao descricao);
}
