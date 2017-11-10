package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Endereco;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@RepositoryRestResource(
		collectionResourceRel = "enderecos",
		itemResourceRel = "endereco",
		path = "enderecos"
)
public interface RepositorioEndereco
		extends RepositorioAuditavel<Endereco, BigInteger, ZonedDateTime, Credencial> {
}
