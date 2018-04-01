package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.Validator;

import java.io.Serializable;

public interface Validavel extends Serializable, Validator {

	void verificarPermissao(Permissao.Descricao permissao)
			throws AccessDeniedException;
}
