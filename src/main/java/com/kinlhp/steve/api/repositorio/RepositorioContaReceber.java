package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Ordem;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.Set;

@RepositoryRestResource(
		collectionResourceRel = "contasreceber",
		itemResourceRel = "contareceber",
		path = "contasreceber"
)
public interface RepositorioContaReceber
		extends RepositorioAuditavel<ContaReceber, Credencial, BigInteger> {

	@RestResource(exported = false)
	Set<ContaReceber> findByOrdem(@Param(value = "ordem") Ordem ordem);
}
