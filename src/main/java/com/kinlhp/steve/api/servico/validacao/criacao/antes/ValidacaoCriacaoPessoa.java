package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPessoa;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

@Component(value = "beforeCreatePessoa")
public class ValidacaoCriacaoPessoa extends ValidacaoPessoa {

	private static final long serialVersionUID = 8658791728896884643L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Pessoa.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Pessoa) object;
		super.erros = errors;

		validarAberturaNascimento();
		validarCnpjCpf();
		validarIeRg();
		validarPerfilUsuario();
		validarTipo();
	}
}
