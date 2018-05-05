package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.CondicaoPagamento;
import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component(value = "beforeSaveContaReceber")
public class ValidacaoAlteracaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = 2556192058902253319L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaReceber) object;
		super.erros = errors;

		// TODO: 4/30/18 implementar design pattern que resolva essa má prática
		validarOrdem();
		validarSituacao();
	}

	@Component
	public final class ValidacaoAlteracaoCondicaoPagamento
			extends JsonDeserializer<CondicaoPagamento> implements Serializable {

		private static final long serialVersionUID = 3021051904802418809L;

		@Override
		public CondicaoPagamento deserialize(JsonParser jsonParser,
		                                     DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaReceber registroInalterado = (ContaReceber) jsonParser
					.getCurrentValue();
			final CondicaoPagamento condicaoPagamento = jsonParser.getCodec()
					.readValue(jsonParser, CondicaoPagamento.class);
			if (registroInalterado.getId() != null && condicaoPagamento != null) {
				if (!registroInalterado.getCondicaoPagamento().equals(condicaoPagamento)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaReceber.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"condicaoPagamento\" inválido: Somente usuário administrador pode alterar condição de pagamento");
				}
			}
			return condicaoPagamento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataVencimento
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = -7225123113685123123L;

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaReceber registroInalterado = (ContaReceber) jsonParser
					.getCurrentValue();
			final LocalDate dataVencimento = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			if (registroInalterado.getId() != null && dataVencimento != null) {
				if (registroInalterado.getDataVencimento().until(dataVencimento, ChronoUnit.DAYS) != 0) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaReceber.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"dataVencimento\" inválido: Somente usuário administrador pode alterar data de vencimento");
				}
			}
			return dataVencimento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoSacado
			extends JsonDeserializer<Pessoa> implements Serializable {

		private static final long serialVersionUID = 335911911630979193L;

		@Override
		public Pessoa deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaReceber registroInalterado = (ContaReceber) jsonParser
					.getCurrentValue();
			final Pessoa sacado = jsonParser.getCodec()
					.readValue(jsonParser, Pessoa.class);
			if (registroInalterado.getId() != null && sacado != null) {
				if (!registroInalterado.getSacado().equals(sacado)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaReceber.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"sacado\" inválido: Somente usuário administrador pode alterar sacado");
				}
			}
			return sacado;
		}
	}

//	@Component
//	public final class ValidacaoAlteracaoSituacao
//			extends JsonDeserializer<ContaReceber.Situacao>
//			implements Serializable {
//
//		private static final long serialVersionUID = 4462880505868022619L;
//
//		@Override
//		public ContaReceber.Situacao deserialize(JsonParser jsonParser,
//		                                         DeserializationContext deserializationContext)
//				throws IOException, JsonProcessingException {
//			final ContaReceber registroInalterado = (ContaReceber) jsonParser
//					.getCurrentValue();
//			final ContaReceber.Situacao situacao = jsonParser.getCodec()
//					.readValue(jsonParser, ContaReceber.Situacao.class);
//			if (registroInalterado.getId() != null && situacao != null) {
//				/*
//				 */
//			}
//			return situacao;
//		}
//	}

	@Component
	public final class ValidacaoAlteracaoValor
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = -8259064977147972333L;

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaReceber registroInalterado = (ContaReceber) jsonParser
					.getCurrentValue();
			final BigDecimal valor = jsonParser.getDecimalValue();
			if (registroInalterado.getId() != null && valor != null) {
				if (registroInalterado.getValor().compareTo(valor) != 0) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaReceber.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"valor\" inválido: Somente usuário administrador pode alterar valor");
				}
			}
			return valor;
		}
	}
}
