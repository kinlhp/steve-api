package com.kinlhp.steve.api.servico.validacao;

import br.com.caelum.stella.validation.CNPJValidator;
import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public abstract class ValidacaoPessoa extends ValidavelAbstrato<Pessoa> {

	private static final long serialVersionUID = -313892552240189447L;

	protected void validarAberturaNascimento() {
		if (super.dominio.getAberturaNascimento() != null) {
			LocalDate data = super.dominio.getAberturaNascimento();
			if (LocalDate.now().until(data, ChronoUnit.DAYS) > 0) {
				// TODO: 3/21/18 implementar internacionalização
				super.erros.rejectValue("aberturaNascimento", "aberturaNascimento.invalid", "Atributo \"aberturaNascimento\" inválido");
			}
		}
	}

	private boolean isCnpjValido(String cnpj) {
		try {
			new CNPJValidator().assertValid(cnpj);
		} catch (InvalidStateException e) {
			return false;
		}
		return true;
	}

	private boolean isCpfValido(String cpf) {
		try {
			new CPFValidator().assertValid(cpf);
		} catch (InvalidStateException e) {
			return false;
		}
		return true;
	}

	protected void validarCnpjCpf() {
		if (super.dominio.getCnpjCpf() != null) {
			if (Pessoa.Tipo.FISICA.equals(super.dominio.getTipo())
					&& !isCpfValido(super.dominio.getCnpjCpf())) {
				// TODO: 3/27/18 implementar internacionalização
				super.erros.rejectValue("cnpjCpf", "cnpjCpf.invalid", "Atributo \"cnpjCpf\" inválido");
			} else if (Pessoa.Tipo.JURIDICA.equals(super.dominio.getTipo())
					&& !isCnpjValido(super.dominio.getCnpjCpf())) {
				// TODO: 3/27/18 implementar internacionalização
				super.erros.rejectValue("cnpjCpf", "cnpjCpf.invalid", "Atributo \"cnpjCpf\" inválido");
			}
		}
	}

	protected void validarIeRg() {
		if (super.dominio.getIeRg() != null) {
			super.dominio
					.setIeRg(super.dominio.getIeRg().toUpperCase(Locale.ROOT));
			if (super.dominio.getIeRg().chars().allMatch(Character::isLetter)
					&& !"ISENTO".equals(super.dominio.getIeRg())) {
				// TODO: 3/27/18 implementar internacionalização
				super.erros.rejectValue("ieRg", "ieRg.invalid", "Atributo \"ieRg\" inválido");
			}
		}
	}

	/**
	 * {@link com.kinlhp.steve.api.dominio.Credencial} sem {@link com.kinlhp.steve.api.dominio.Permissao} de {@link com.kinlhp.steve.api.dominio.Permissao.Descricao#ADMINISTRADOR}
	 * não pode incluir {@link Pessoa} com perfil de usuário
	 */
	protected void validarPerfilUsuario() {
		if (super.dominio.isPerfilUsuario()) {
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR);
			if (super.dominio.getTipo() != null) {
				if (Pessoa.Tipo.JURIDICA.equals(super.dominio.getTipo())
						&& super.dominio.isPerfilUsuario()) {
					// TODO: 3/27/18 implementar internacionalizacao
					super.erros.rejectValue("perfilUsuario", "perfilUsuario.invalid", "Atributo \"perfilUsuario\" inválido: Somente pessoa física pode ter perfil de usuário");
				}
			}
		}
	}

	protected void validarTipo() {
		if (super.dominio.getTipo() != null) {
			if (Pessoa.Tipo.SISTEMA.equals(super.dominio.getTipo())) {
				// TODO: 3/27/18 implementar internacionalizacao
				super.erros.rejectValue("tipo", "tipo.invalid", "Atributo \"tipo\" inválido: Pessoa deve ser física ou jurídica");
			}
		}
	}
}
