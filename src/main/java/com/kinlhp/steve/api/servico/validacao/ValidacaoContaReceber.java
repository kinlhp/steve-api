package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;

public abstract class ValidacaoContaReceber
		extends ValidavelAbstrato<ContaReceber, BigInteger> {

	private static final long serialVersionUID = 7161017010120246928L;

	public ValidacaoContaReceber(@Autowired RepositorioContaReceber repositorio) {
		super(repositorio);
	}

	protected void validarOrdem() {
		if (super.dominio.getOrdem() != null) {
			if (!Ordem.Situacao.FINALIZADO.equals(super.dominio.getOrdem().getSituacao())) {
				// TODO: 5/1/18 implementar internacionalização
				super.erros.rejectValue("ordem", "ordem.invalid", "Atributo \"ordem\" inválido: Somente ordem finalizada gera conta a receber");
			}
		}
	}

	protected void validarSituacao() {
		if (super.dominio.getSituacao() != null) {
			// se conta a receber possuir montante pago
			if (super.dominio.hasMontantePago()) {
				if (ContaReceber.Situacao.ABERTO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalização
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber com montante pago não deve ser aberto");
				} else if (ContaReceber.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalização
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber com montante pago não deve ser cancelado");
				} else if (ContaReceber.Situacao.BAIXADO.equals(super.dominio.getSituacao()) && super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalização
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber com saldo devedor não deve ser baixado");
				} else if (ContaReceber.Situacao.AMORTIZADO.equals(super.dominio.getSituacao()) && !super.dominio.hasSaldoDevedor()) {
					// TODO: 5/1/18 implementar internacionalização
					super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber sem saldo devedor não deve ser amortizado");
				}
			}
			// se conta a receber não possuir montante pago
			else {
				if (ContaReceber.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 5/1/18 implementar internacionalização
					super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
							"Atributo \"situacao\" inválido: Somente usuário administrador pode cancelar conta a receber");
				} else if (!ContaReceber.Situacao.ABERTO.equals(super.dominio.getSituacao())) {
					if (ContaReceber.Situacao.AMORTIZADO.equals(super.dominio.getSituacao())) {
						// TODO: 5/1/18 implementar internacionalização
						super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber sem montante pago não deve ser amortizado");
					} else if (ContaReceber.Situacao.BAIXADO.equals(super.dominio.getSituacao())) {
						// TODO: 5/1/18 implementar internacionalização
						super.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Conta a receber sem montante pago não deve ser baixado");
					}
				}
			}
		}
	}
}
