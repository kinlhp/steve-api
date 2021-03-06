package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Persistivel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.Validator;

import java.io.Serializable;

public interface Validavel<T extends Persistivel>
		extends Serializable, Validator {

	void verificarPermissao(Permissao.Descricao permissao)
			throws AccessDeniedException;

	void verificarPermissao(Permissao.Descricao permissao,
	                        String motivoAcessoNegado)
			throws AccessDeniedException;
}
