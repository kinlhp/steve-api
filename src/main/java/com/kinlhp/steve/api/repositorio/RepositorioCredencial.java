package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "credenciais",
		itemResourceRel = "credencial",
		path = "credenciais"
)
public interface RepositorioCredencial
		extends RepositorioAuditavel<Credencial, Credencial, BigInteger> {
}
