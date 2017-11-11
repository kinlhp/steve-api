package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Ordem;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "ordens",
		itemResourceRel = "ordem",
		path = "ordens"
)
public interface RepositorioOrdem
		extends RepositorioAuditavel<Ordem, Credencial, BigInteger> {
}
