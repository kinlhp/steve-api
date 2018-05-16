package com.kinlhp.steve.api.servico.validacao.criacao.antes;

import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaPagar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component(value = "beforeCreateContaPagar")
public class ValidacaoCriacaoContaPagar extends ValidacaoContaPagar {

	private static final long serialVersionUID = 5943263452776231374L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaPagar) object;
		super.erros = errors;

		validarDataEmissao();
		validarDataVencimento();
		validarMesReferente();
		validarSituacao();
	}

	private void validarDataVencimento() {
		if (super.dominio.getDataVencimento() != null) {
			final LocalDate data = super.dominio.getDataVencimento();
			if (LocalDate.now().until(data, ChronoUnit.DAYS) < 0) {
				// TODO: 5/1/18 implementar internacionalização
				super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
						"Atributo \"dataEmissao\" inválido: Somente usuário administrador pode definir data de vencimento retroativo");
			}
		}
	}
}
