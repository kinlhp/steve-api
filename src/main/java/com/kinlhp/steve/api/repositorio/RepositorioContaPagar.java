package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "contaspagar",
		itemResourceRel = "contapagar",
		path = "contaspagar"
)
public interface RepositorioContaPagar
		extends RepositorioAuditavel<ContaPagar, Credencial, BigInteger> {
}
