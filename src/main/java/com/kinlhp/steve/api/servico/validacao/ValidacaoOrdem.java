package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;

import java.math.BigInteger;
import java.util.Locale;

public abstract class ValidacaoOrdem extends ValidavelAbstrato<Ordem> {

	private static final long serialVersionUID = -6289700643137865789L;

	protected void validarCliente() {
		if (super.dominio.getCliente() != null) {
			if (BigInteger.ZERO.compareTo(super.dominio.getCliente().getId()) >= 0
					|| !super.dominio.getCliente().isPerfilCliente()) {
				// TODO: 4/7/18 implementar internacionalizacao
				super.erros.rejectValue("cliente", "cliente.invalid", "Atributo \"cliente\" inválido: Somente pessoa com perfil de cliente pode ter " + super.dominio.getTipo().getDescricao().toLowerCase(Locale.ROOT));
			}
		}
	}

	protected void validarSituacao() {
		if (super.dominio.getSituacao() != null) {
			if (Ordem.Tipo.ORCAMENTO.equals(super.dominio.getTipo())) {
				validarSituacaoOrcamento();
			}
			if (Ordem.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
				// TODO: 4/7/18 implementar internacionalizacao
				verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Somente usuário administrador pode definir ordem com situação cancelado");
			}
		}
	}

	private void validarSituacaoOrcamento() {
		if (!Ordem.Situacao.ABERTO.equals(super.dominio.getSituacao())
				&& !Ordem.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
			// TODO: 4/7/18 implementar internacionalizacao
			super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Orçamento pode estar somente em aberto ou cancelado");
		}
	}
}
