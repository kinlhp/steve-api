package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.math.BigDecimal;

@Component(value = "beforeCreateMovimentacaoContaPagar")
public class ValidacaoCriacaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = -7230953329954934949L;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaPagar) object;
		super.erros = errors;

		// TODO: 5/6/18 implementar design pattern que resolva essa má prática
		validarBaseCalculo();
		validarContaPagar();
		validarEstornado();
		validarSaldoDevedor();
	}

	private void validarBaseCalculo() {
		super.dominio
				.setBaseCalculo(super.dominio.getContaPagar().getSaldoDevedor());
	}

	private void validarContaPagar() {
		if (super.dominio.getContaPagar() != null) {
			if (ContaPagar.Situacao.BAIXADO.equals(super.dominio.getContaPagar().getSituacao())) {
				// TODO: 5/6/18 implementar internacionalização
				super.erros.rejectValue("valorPago", "valorPago.invalid", "Atributo \"valorPago\" inválido: Movimentação em conta a pagar baixado não é permitido");
			} else if (ContaPagar.Situacao.CANCELADO.equals(super.dominio.getContaPagar().getSituacao())) {
				// TODO: 5/6/18 implementar internacionalização
				super.erros.rejectValue("valorPago", "valorPago.invalid", "Atributo \"valorPago\" inválido: Movimentação em conta a pagar cancelado não é permitido");
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
