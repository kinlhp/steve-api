package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Ordem;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.math.BigInteger;
import java.util.Set;

@RepositoryRestResource(
		collectionResourceRel = "itensordemservico",
		itemResourceRel = "itemordemservico",
		path = "itensordemservico"
)
public interface RepositorioItemOrdemServico
		extends RepositorioAuditavel<ItemOrdemServico, Credencial, BigInteger> {

	@RestResource(exported = false)
	Set<ItemOrdemServico> findByOrdem(@Param(value = "ordem") Ordem ordem);
}
