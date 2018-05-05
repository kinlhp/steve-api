package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaPagar;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;

@Component(value = "beforeSaveContaPagar")
public class ValidacaoAlteracaoContaPagar extends ValidacaoContaPagar {

	private static final long serialVersionUID = -8693750234140765319L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaPagar) object;
		super.erros = errors;

		// TODO: 5/1/18 implementar design pattern que resolva essa má prática
		validarDataEmissao();
		validarMesReferente();
		validarSituacao();
	}

	@Component
	public final class ValidacaoAlteracaoCedente
			extends JsonDeserializer<Pessoa> implements Serializable {

		private static final long serialVersionUID = 4034000536988192915L;

		@Override
		public Pessoa deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final Pessoa cedente = jsonParser.getCodec()
					.readValue(jsonParser, Pessoa.class);
			if (registroInalterado.getId() != null && cedente != null) {
				if (!registroInalterado.getCedente().equals(cedente)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"cedente\" inválido: Somente usuário administrador pode alterar cedente");
				}
			}
			return cedente;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataEmissao
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 6965593616665739518L;

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final LocalDate dataEmissao = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			if (registroInalterado.getId() != null
					&& registroInalterado.getDataEmissao() != null) {
				if (!registroInalterado.getDataEmissao().equals(dataEmissao)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Atributo \"dataEmissao\" inválido: Somente usuário administrador pode alterar data de emissão");
				}
			}
			return dataEmissao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataVencimento
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 6509551627925717353L;

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final LocalDate dataVencimento = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			if (registroInalterado.getId() != null && dataVencimento != null) {
				if (registroInalterado.getDataVencimento().until(dataVencimento, ChronoUnit.DAYS) != 0) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"dataVencimento\" inválido: Somente usuário administrador pode alterar data de vencimento");
				}
			}
			return dataVencimento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoFatura extends JsonDeserializer<String>
			implements Serializable {

		private static final long serialVersionUID = -4782538323213302710L;

		@Override
		public String deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final String fatura = jsonParser.getText();
			if (registroInalterado.getId() != null && fatura != null) {
				if (!registroInalterado.getFatura().equals(fatura)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"fatura\" inválido: Somente usuário administrador pode alterar fatura");
				}
			}
			return fatura;
		}
	}

	@Component
	public final class ValidacaoAlteracaoMesReferente
			extends JsonDeserializer<YearMonth> implements Serializable {

		private static final long serialVersionUID = -837015038146254004L;

		@Override
		public YearMonth deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final YearMonth mesReferente = jsonParser.getCodec()
					.readValue(jsonParser, YearMonth.class);
			if (registroInalterado.getId() != null && mesReferente != null) {
				if (registroInalterado.getMesReferente().until(mesReferente, ChronoUnit.MONTHS) != 0) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"mesReferente\" inválido: Somente usuário administrador pode alterar mês referente");
				}
			}
			return mesReferente;
		}
	}

//	@Component
//	public final class ValidacaoAlteracaoSituacao
//			extends JsonDeserializer<ContaPagar.Situacao>
//			implements Serializable {
//
//		private static final long serialVersionUID = -1161610604630973957L;
//
//		@Override
//		public ContaPagar.Situacao deserialize(JsonParser jsonParser,
//		                                       DeserializationContext deserializationContext)
//				throws IOException, JsonProcessingException {
//			final ContaPagar registroInalterado = (ContaPagar) jsonParser
//					.getCurrentValue();
//			final ContaPagar.Situacao situacao = jsonParser.getCodec()
//					.readValue(jsonParser, ContaPagar.Situacao.class);
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

		private static final long serialVersionUID = -4164485414381354423L;

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ContaPagar registroInalterado = (ContaPagar) jsonParser
					.getCurrentValue();
			final BigDecimal valor = jsonParser.getDecimalValue();
			if (registroInalterado.getId() != null && valor != null) {
				if (registroInalterado.getValor().compareTo(valor) != 0) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoContaPagar.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"valor\" inválido: Somente usuário administrador pode alterar valor");
				}
			}
			return valor;
		}
	}
}
