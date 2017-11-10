package com.kinlhp.steve.api.repositorio;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.dominio.Email;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@RepositoryRestResource(
		collectionResourceRel = "emails",
		itemResourceRel = "email",
		path = "emails"
)
public interface RepositorioEmail
		extends RepositorioAuditavel<Email, BigInteger, ZonedDateTime, Credencial> {
}
