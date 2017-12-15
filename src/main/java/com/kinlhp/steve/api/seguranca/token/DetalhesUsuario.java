package com.kinlhp.steve.api.seguranca.token;

import com.kinlhp.steve.api.dominio.Credencial;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.Collection;

public class DetalhesUsuario<U extends Serializable> extends User {

	private static final long serialVersionUID = SpringSecurityCoreVersion
			.SERIAL_VERSION_UID;

	private final U auditor;

	public DetalhesUsuario(U auditor,
	                       Collection<? extends GrantedAuthority> autoridades) {
		super(((Credencial) auditor).getUsuario(), ((Credencial) auditor).getSenha(), autoridades);
		this.auditor = auditor;
	}

	public U getAuditor() {
		return auditor;
	}
}
