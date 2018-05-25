package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
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
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends Credencial> S save(S entity);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends Credencial> List<S> save(Iterable<S> credenciais);

	@Override
	@PreAuthorize(value = "hasAuthority('ADMINISTRADOR') and #oauth2.hasScope('escrita')")
	<S extends Credencial> S saveAndFlush(S entity);

	@RestResource(path = "usuario", rel = "usuario")
	Optional<Credencial> findByUsuario(@Param(value = "usuario") String usuario);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@Query(value = "SELECT c " +
			"FROM #{#entityName} c " +
			"WHERE c.usuario LIKE %:usuario% " +
			"OR c.funcionario.cnpjCpf = :cpf")
	@RestResource(path = "usuario-cpf", rel = "usuario-cpf")
	Page<Credencial> findByUsuarioOrCpf(@Param(value = "usuario") String usuario,
	                                    @Param(value = "cpf") String cpf,
	                                    Pageable pageable);
}
