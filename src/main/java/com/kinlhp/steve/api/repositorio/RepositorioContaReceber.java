package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Ordem;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.Set;

@RepositoryRestResource(
		collectionResourceRel = "contasReceber",
		itemResourceRel = "contaReceber",
		path = "contasReceber"
)
public interface RepositorioContaReceber
		extends RepositorioAuditavel<ContaReceber, Credencial, BigInteger> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(exported = false)
	Set<ContaReceber> findByOrdem(@Param(value = "ordem") Ordem ordem);
}
