package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Pessoa;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "pessoas",
		itemResourceRel = "pessoa",
		path = "pessoas"
)
public interface RepositorioPessoa
		extends RepositorioAuditavel<Pessoa, Credencial, BigInteger> {
}
