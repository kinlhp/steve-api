package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "movimentacoesContaReceber",
		itemResourceRel = "movimentacaoContaReceber",
		path = "movimentacoesContaReceber"
)
public interface RepositorioMovimentacaoContaReceber
		extends RepositorioAuditavel<MovimentacaoContaReceber, Credencial, BigInteger> {
}
