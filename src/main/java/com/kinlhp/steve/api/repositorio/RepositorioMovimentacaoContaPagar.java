package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;
import java.util.Set;

@RepositoryRestResource(
		collectionResourceRel = "movimentacoesContaPagar",
		itemResourceRel = "movimentacaoContaPagar",
		path = "movimentacoesContaPagar"
)
public interface RepositorioMovimentacaoContaPagar
		extends RepositorioAuditavel<MovimentacaoContaPagar, Credencial, BigInteger> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(exported = false)
	Set<MovimentacaoContaPagar> findByContaPagar(@Param(value = "contaPagar") ContaPagar contaPagar);
}
