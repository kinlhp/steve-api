package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.FormaPagamento;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@RepositoryRestResource(
		collectionResourceRel = "formaspagamento",
		itemResourceRel = "formapagamento",
		path = "formaspagamento"
)
public interface RepositorioFormaPagamento
		extends RepositorioAuditavel<FormaPagamento, BigInteger, ZonedDateTime, Credencial> {
}
