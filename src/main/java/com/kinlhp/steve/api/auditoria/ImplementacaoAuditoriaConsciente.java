package com.kinlhp.steve.api.auditoria;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.repositorio.RepositorioCredencial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;

import java.io.Serializable;
import java.math.BigInteger;

public class ImplementacaoAuditoriaConsciente
		implements AuditorAware<Credencial>, Serializable {

	private static final long serialVersionUID = 9153562147653185333L;

	@Autowired
	private RepositorioCredencial repositorioCredencial;

	@Override
	public Credencial getCurrentAuditor() {
		return repositorioCredencial.findOne(BigInteger.ZERO);
	}
}
