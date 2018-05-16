package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

@Component(value = "beforeCreateMovimentacaoContaReceber")
public class ValidacaoCriacaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = 1370795806344886904L;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaReceber) object;
		super.erros = errors;

		validarBaseCalculo();
		validarContaReceber();
		validarEstornado();
		validarSaldoDevedor();
	}

	private void validarBaseCalculo() {
		super.dominio
				.setBaseCalculo(super.dominio.getContaReceber().getSaldoDevedor());
	}

	private void validarContaReceber() {
		if (super.dominio.getContaReceber() != null) {
			if (ContaReceber.Situacao.BAIXADO.equals(super.dominio.getContaReceber().getSituacao())) {
				// TODO: 5/5/18 implementar internacionalização
				super.erros.rejectValue("valorPago", "valorPago.invalid", "Atributo \"valorPago\" inválido: Movimentação em conta a receber baixado não é permitido");
			} else if (ContaReceber.Situacao.CANCELADO.equals(super.dominio.getContaReceber().getSituacao())) {
				// TODO: 5/5/18 implementar internacionalização
				super.erros.rejectValue("valorPago", "valorPago.invalid", "Atributo \"valorPago\" inválido: Movimentação em conta a receber cancelado não é permitido");
			}
		}
	}

	private void validarEstornado() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Somente movimentação existente pode ser estornado");
		}
	}

	private void validarSaldoDevedor() {
		final BigDecimal saldoDevedor = super.dominio.getBaseCalculo()
				.add(super.dominio.getJuroAplicado())
				.subtract(super.dominio.getDescontoConcedido())
				.subtract(super.dominio.getValorPago());
		super.dominio.setSaldoDevedor(saldoDevedor);
	}
}
