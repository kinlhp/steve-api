package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "pessoas",
		itemResourceRel = "pessoa",
		path = "pessoas"
)
public interface RepositorioPessoa
		extends RepositorioAuditavel<Pessoa, Credencial, BigInteger> {

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@Query(value = "SELECT p " +
			"FROM #{#entityName} p " +
			"WHERE p.cnpjCpf = :cnpjCpf " +
			"OR p.nomeRazao LIKE %:nomeRazao% " +
			"OR (p.fantasiaSobrenome IS NOT NULL AND p.fantasiaSobrenome LIKE %:fantasiaSobrenome%)")
	@RestResource(
			path = "cnpjCpf-nomeRazao-fantasiaSobrenome",
			rel = "cnpjCpf-nomeRazao-fantasiaSobrenome"
	)
	Page<Pessoa> findByCnpjCpfOrNomeRazaoOrFantasiaSobrenome(@Param(value = "cnpjCpf") String cnpjCpf,
	                                                         @Param(value = "nomeRazao") String nomeRazao,
	                                                         @Param(value = "fantasiaSobrenome") String fantasiaSobrenome,
	                                                         Pageable pageable);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "perfilCliente", rel = "perfilCliente")
	Page<Pessoa> findByPerfilClienteIsTrue(Pageable pageable);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "perfilFornecedor", rel = "perfilFornecedor")
	Page<Pessoa> findByPerfilFornecedorIsTrue(Pageable pageable);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "perfilTransportador", rel = "perfilTransportador")
	Page<Pessoa> findByPerfilTransportadorIsTrue(Pageable pageable);

	@PreAuthorize(value = "hasAuthority('PADRAO') and #oauth2.hasScope('leitura')")
	@RestResource(path = "perfilUsuario", rel = "perfilUsuario")
	Page<Pessoa> findByPerfilUsuarioIsTrue(Pageable pageable);
}
