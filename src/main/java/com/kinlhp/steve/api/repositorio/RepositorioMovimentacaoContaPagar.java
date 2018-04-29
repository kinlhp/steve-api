package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "movimentacoescontapagar",
		itemResourceRel = "movimentacaocontapagar",
		path = "movimentacoescontapagar"
)
public interface RepositorioMovimentacaoContaPagar
		extends RepositorioAuditavel<MovimentacaoContaPagar, Credencial, BigInteger> {
}
