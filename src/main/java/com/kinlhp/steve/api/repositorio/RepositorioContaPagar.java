package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "contasPagar",
		itemResourceRel = "contaPagar",
		path = "contasPagar"
)
public interface RepositorioContaPagar
		extends RepositorioAuditavel<ContaPagar, Credencial, BigInteger> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@Query(value = "SELECT c " +
			"FROM #{#entityName} c " +
			"WHERE c.cedente.cnpjCpf = :cnpjCpf " +
			"OR c.cedente.nomeRazao LIKE %:nomeRazao% " +
			"OR (c.cedente.fantasiaSobrenome IS NOT NULL AND c.cedente.fantasiaSobrenome LIKE %:fantasiaSobrenome%)")
	@RestResource(
			path = "cnpjCpf-nomeRazao-fantasiaSobrenome",
			rel = "cnpjCpf-nomeRazao-fantasiaSobrenome"
	)
	Page<ContaPagar> findByCnpjCpfOrNomeRazaoOrFantasiaSobrenome(@Param(value = "cnpjCpf") String cnpjCpf,
	                                                             @Param(value = "nomeRazao") String nomeRazao,
	                                                             @Param(value = "fantasiaSobrenome") String fantasiaSobrenome,
	                                                             Pageable pageable);
}
