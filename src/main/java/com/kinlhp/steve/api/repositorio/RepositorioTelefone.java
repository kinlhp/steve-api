package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Telefone;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "telefones",
		itemResourceRel = "telefone",
		path = "telefones"
)
public interface RepositorioTelefone
		extends RepositorioAuditavel<Telefone, Credencial, BigInteger> {
}
