package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Credencial;

import java.math.BigInteger;

public abstract class ValidacaoCredencial
		extends ValidavelAbstrato<Credencial> {

	private static final long serialVersionUID = -853491569703403137L;

	protected void validarFuncionario() {
		if (super.dominio.getFuncionario() != null) {
			if (BigInteger.ZERO.compareTo(super.dominio.getFuncionario().getId()) >= 0
					|| !super.dominio.getFuncionario().isPerfilUsuario()) {
				// TODO: 4/4/18 implementar internacionalizacao
				super.erros.rejectValue("funcionario", "funcionario.invalid", "Atributo \"funcionario\" inválido: Somente pessoa com perfil de usuário pode ter credencial");
			}
		}
	}
}
