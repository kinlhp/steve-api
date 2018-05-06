package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

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

		// TODO: 5/5/18 implementar design pattern que resolva essa má prática
		verificarSituacaoContaReceber();
		validarEstorno();
	}

	private void validarEstorno() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Somente movimentação existente pode ser estornado");
		}
	}

	private void verificarSituacaoContaReceber() {
		if (super.dominio.getContaReceber() != null) {
			if (!ContaReceber.Situacao.ABERTO.equals(super.dominio.getContaReceber().getSituacao())
					&& !ContaReceber.Situacao.AMORTIZADO.equals(super.dominio.getContaReceber().getSituacao())) {
				// TODO: 5/5/18 implementar internacionalização
				super.erros.rejectValue("valorPago", "valorPago.invalid", "Atributo \"valorPago\" inválido: Movimentação em conta a receber baixado ou cancelado não é permitido");
			}
		}
	}
}
