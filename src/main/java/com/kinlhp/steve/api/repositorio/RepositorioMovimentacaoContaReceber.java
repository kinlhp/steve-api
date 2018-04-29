package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "movimentacoescontareceber",
		itemResourceRel = "movimentacaocontareceber",
		path = "movimentacoescontareceber"
)
public interface RepositorioMovimentacaoContaReceber
		extends RepositorioAuditavel<MovimentacaoContaReceber, Credencial, BigInteger> {
}
