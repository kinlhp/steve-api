package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class ValidacaoContaReceber
		extends ValidavelAbstrato<ContaReceber> {

	private static final long serialVersionUID = 5963001361937877233L;

	protected void validarDataVencimento() {
		if (super.dominio.getDataVencimento() != null) {
			LocalDate data = super.dominio.getDataVencimento();
			if (LocalDate.now().until(data, ChronoUnit.DAYS) < 0) {
				// TODO: 5/1/18 implementar internacionalizacao
				super.erros.rejectValue("dataVencimento", "dataVencimento.invalid", "Atributo \"dataVencimento\" inválido: Somente data futura é permitido");
			}
		}
	}

	protected void validarOrdem() {
		if (super.dominio.getOrdem() != null) {
			if (!Ordem.Situacao.FINALIZADO.equals(super.dominio.getOrdem().getSituacao())) {
				// TODO: 5/1/18 implementar internacionalizacao
				super.erros.rejectValue("ordem", "ordem.invalid", "Atributo \"ordem\" inválido: Somente ordem finalizada gera conta a receber");
			}
		}
	}

	protected void validarSituacao() {
		if (super.dominio.getSituacao() != null) {
			// se conta a receber possuir montante pago
			if (BigDecimal.ZERO.compareTo(super.dominio.getMontantePago()) <= 0) {
				if (ContaReceber.Situacao.ABERTO.equals(super.dominio.getSituacao())
						|| ContaReceber.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber com montante pago deve ser amortizado ou baixado");
				} else if (ContaReceber.Situacao.BAIXADO.equals(super.dominio.getSituacao()) && super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber com saldo devedor não deve ser baixado");
				} else if (ContaReceber.Situacao.AMORTIZADO.equals(super.dominio.getSituacao()) && !super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber sem saldo devedor não deve ser amortizado");
				}
			}
			// se conta a receber não possuir montante pago
			else {
				if (ContaReceber.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
							"Atributo \"situacao\" inválido: Somente usuário administrador pode cancelar conta a receber");
				} else if (!ContaReceber.Situacao.ABERTO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalizacao
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber sem montante pago não deve ser amortizado ou baixado");
				}
			}
		}
	}
}
