package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Servico;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;

@RepositoryRestResource(
		collectionResourceRel = "servicos",
		itemResourceRel = "servico",
		path = "servicos"
)
public interface RepositorioServico
		extends RepositorioAuditavel<Servico, Credencial, BigInteger> {
}
