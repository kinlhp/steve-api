package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;

@Component(value = "beforeSaveMovimentacaoContaPagar")
public class ValidacaoAlteracaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = 1735428040363087863L;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaPagar) object;
		super.erros = errors;

		// TODO: 5/6/18 implementar design pattern que resolva essa má prática
		validarEstornado();
	}

	private void validarEstornado() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"estornado\" inválido: Somente usuário administrador pode estornar movimentação de conta a pagar");
		}
	}

	@Component
	public final class ValidacaoAlteracaoEstornado
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = -492706165867510931L;

		@Override
		public Boolean deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final MovimentacaoContaPagar registroInalterado = (MovimentacaoContaPagar) jsonParser
					.getCurrentValue();
			final boolean estornado = jsonParser.getBooleanValue();
			if (registroInalterado.getId() != null) {
				if (registroInalterado.isEstornado() && !estornado) {
					ValidacaoAlteracaoMovimentacaoContaPagar.this
							.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Estorno de movimentação de conta a pagar não pode ser cancelado");
				}
			}
			return estornado;
		}
	}
}
