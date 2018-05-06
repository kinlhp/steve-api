package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;

@Component(value = "beforeSaveMovimentacaoContaReceber")
public class ValidacaoAlteracaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -7834254533728455876L;

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaReceber) object;
		super.erros = errors;

		// TODO: 5/5/18 implementar design pattern que resolva essa má prática
		validarEstorno();
	}

	private void validarEstorno() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"estornado\" inválido: Somente usuário administrador pode estornar movimentação de conta a receber");
		}
	}

	@Component
	public final class ValidacaoAlteracaoEstorno
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = -4655975989176823695L;

		@Override
		public Boolean deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final MovimentacaoContaReceber registroInalterado = (MovimentacaoContaReceber) jsonParser
					.getCurrentValue();
			final boolean estornado = jsonParser.getBooleanValue();
			if (registroInalterado.getId() != null) {
				if (registroInalterado.isEstornado() && !estornado) {
					ValidacaoAlteracaoMovimentacaoContaReceber.this
							.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Estorno de movimentação de conta a receber não pode ser cancelado");
				}
			}
			return estornado;
		}
	}
}
