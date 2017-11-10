package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Uf;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@RepositoryRestResource(
		collectionResourceRel = "ufs",
		itemResourceRel = "uf",
		path = "ufs"
)
public interface RepositorioUf
		extends RepositorioAuditavel<Uf, BigInteger, ZonedDateTime, Credencial> {
}
