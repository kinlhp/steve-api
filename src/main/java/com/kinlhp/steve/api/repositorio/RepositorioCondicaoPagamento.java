package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.CondicaoPagamento;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "condicoespagamento",
		itemResourceRel = "condicaopagamento",
		path = "condicoespagamento"
)
public interface RepositorioCondicaoPagamento
		extends RepositorioAuditavel<CondicaoPagamento, Credencial, BigInteger> {
}
