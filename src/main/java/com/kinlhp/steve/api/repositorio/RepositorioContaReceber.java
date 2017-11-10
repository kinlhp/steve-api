package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@RepositoryRestResource(
		collectionResourceRel = "contasreceber",
		itemResourceRel = "contareceber",
		path = "contasreceber"
)
public interface RepositorioContaReceber
		extends RepositorioAuditavel<ContaReceber, BigInteger, ZonedDateTime, Credencial> {
}
