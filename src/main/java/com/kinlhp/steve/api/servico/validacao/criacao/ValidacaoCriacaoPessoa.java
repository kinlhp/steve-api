package com.kinlhp.steve.api.servico.validacao.criacao;

import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPessoa;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreatePessoa")
public class ValidacaoCriacaoPessoa extends ValidacaoPessoa {

	private static final long serialVersionUID = -1913297021056622137L;

	@Override
	public boolean supports(Class<?> classe) {
		return Pessoa.class.equals(classe);
	}

	@Override
	public void validate(Object objeto, Errors errors) {
		super.dominio = (Pessoa) objeto;
		super.erros = errors;

		// TODO: 3/21/18 implementar design pattern que resolva essa má prática
		validarAberturaNascimento();
		validarCnpjCpf();
		validarIeRg();
		validarPerfilUsuario();
		validarTipo();
	}
}
