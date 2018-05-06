package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

public abstract class ValidacaoContaPagar extends ValidavelAbstrato<ContaPagar> {

	private static final long serialVersionUID = 5971908849306934779L;

	protected void validarDataEmissao() {
		if (super.dominio.getDataEmissao() != null) {
			final LocalDate data = super.dominio.getDataEmissao();
			if (LocalDate.now().until(data, ChronoUnit.DAYS) > 0) {
				// TODO: 5/1/18 implementar internacionalizacao
				super.erros.rejectValue("dataEmissao", "dataEmissao.invalid", "Atributo \"dataEmissao\" inválido: Data futura não é permitido");
			}
		}
	}

	protected void validarMesReferente() {
		if (super.dominio.getMesReferente() != null) {
			final YearMonth data = super.dominio.getMesReferente();
			if (YearMonth.now().until(data, ChronoUnit.MONTHS) > 0) {
				// TODO: 5/1/18 implementar internacionalizacao
				super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
						"Atributo \"mesReferente\" inválido: Somente usuário administrador pode definir mês referente futuro");
			}
		}
	}

	protected void validarSituacao() {
		if (super.dominio.getSituacao() != null) {
			// se conta a pagar possuir montante pago
			if (super.dominio.hasMontantePago()) {
				if (ContaPagar.Situacao.ABERTO.equals(super.dominio.getSituacao())
						|| ContaPagar.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a pagar com montante pago deve ser amortizado ou baixado");
				} else if (ContaPagar.Situacao.BAIXADO.equals(super.dominio.getSituacao()) && super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a pagar com saldo devedor não deve ser baixado");
				} else if (ContaPagar.Situacao.AMORTIZADO.equals(super.dominio.getSituacao()) && !super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a pagar sem saldo devedor não deve ser amortizado");
				}
			}
			// se conta a pagar não possuir montante pago
			else {
				if (ContaPagar.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
							"Atributo \"situacao\" inválido: Somente usuário administrador pode cancelar conta a pagar");
				} else if (!ContaPagar.Situacao.ABERTO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a pagar sem montante pago não deve ser amortizado ou baixado");
				}
			}
		}
	}
}
