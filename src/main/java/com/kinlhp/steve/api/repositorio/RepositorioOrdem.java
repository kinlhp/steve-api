package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Ordem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "ordens",
		itemResourceRel = "ordem",
		path = "ordens"
)
public interface RepositorioOrdem
		extends RepositorioAuditavel<Ordem, Credencial, BigInteger> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@Query(value = "SELECT o " +
			"FROM #{#entityName} o " +
			"WHERE o.id = :id " +
			"OR o.cliente.cnpjCpf = :cnpjCpf")
	@RestResource(path = "id-cnpjCpf", rel = "id-cnpjCpf")
	Page<Ordem> findByIdOrCnpjCpf(@Param(value = "id") BigInteger id,
	                              @Param(value = "cnpjCpf") String cnpjCpf,
	                              Pageable pageable);
}
