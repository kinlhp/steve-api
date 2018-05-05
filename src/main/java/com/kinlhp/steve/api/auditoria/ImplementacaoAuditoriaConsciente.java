package com.kinlhp.steve.api.auditoria;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.seguranca.servico.ImplementacaoServicoDetalhesUsuario;
import com.kinlhp.steve.api.seguranca.token.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

public class ImplementacaoAuditoriaConsciente
		implements AuditorAware<Credencial>, Serializable {

	private static final long serialVersionUID = 1091854548304255178L;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private ImplementacaoServicoDetalhesUsuario servicoDetalhesUsuario;

	@Override
	public Credencial getCurrentAuditor() {
		String usuario = SecurityContextHolder.getContext().getAuthentication()
				.getName();
		try {
			entityManager.setFlushMode(FlushModeType.COMMIT);
			return (Credencial) ((DetalhesUsuario) servicoDetalhesUsuario
					.loadUserByUsername(usuario)).getAuditor();
		} finally {
			entityManager.setFlushMode(FlushModeType.AUTO);
		}
	}
}
