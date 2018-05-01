package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public abstract class ValidacaoItemOrdemServico
		extends ValidavelAbstrato<ItemOrdemServico> {

	private static final long serialVersionUID = -7904557098113855937L;

	protected void validarDataFinalizacaoPrevista() {
		if (super.dominio.getDataFinalizacaoPrevista() != null) {
			LocalDate data = super.dominio.getDataFinalizacaoPrevista();
			if (LocalDate.now().until(data, ChronoUnit.DAYS) < 0) {
				super.erros.rejectValue("dataFinalizacaoPrevista", "dataFinalizacaoPrevista.invalid", "Atributo \"dataFinalizacaoPrevista\" inválido: Somente data futura é permitido");
			}
		}
	}
}
