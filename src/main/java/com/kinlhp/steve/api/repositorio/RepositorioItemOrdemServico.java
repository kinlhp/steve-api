package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "itensordemservico",
		itemResourceRel = "itemordemservico",
		path = "itensordemservico"
)
public interface RepositorioItemOrdemServico
		extends RepositorioAuditavel<ItemOrdemServico, Credencial, BigInteger> {
}
