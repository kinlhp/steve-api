package com.kinlhp.steve.api.auditoria;

import com.kinlhp.steve.api.dominio.Credencial;
import com.kinlhp.steve.api.seguranca.servico.ImplementacaoServicoDetalhesUsuario;
import com.kinlhp.steve.api.seguranca.token.DetalhesUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.io.Serializable;

@Component(value = "implementacaoAuditoriaConsciente")
public class ImplementacaoAuditoriaConsciente
		implements AuditorAware<Credencial>, Serializable {

	private static final long serialVersionUID = -3644081520943535674L;
	private final ImplementacaoServicoDetalhesUsuario servicoDetalhesUsuario;

	@PersistenceContext
	private EntityManager entityManager;

	public ImplementacaoAuditoriaConsciente(@Autowired ImplementacaoServicoDetalhesUsuario servicoDetalhesUsuario) {
		this.servicoDetalhesUsuario = servicoDetalhesUsuario;
	}

	// TODO: 5/15/18 verificar se há necessidade de alteração no EntityManager
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
