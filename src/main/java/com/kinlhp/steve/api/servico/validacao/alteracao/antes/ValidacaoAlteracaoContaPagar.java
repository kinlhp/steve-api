package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.ContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.repositorio.RepositorioContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component(value = "beforeSaveContaPagar")
public class ValidacaoAlteracaoContaPagar extends ValidacaoContaPagar {

	private static final long serialVersionUID = 6680629921535435654L;

	public ValidacaoAlteracaoContaPagar(@Autowired RepositorioContaPagar repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaPagar) object;
		super.erros = errors;

		validarDataEmissao();
		validarMesReferente();
		validarSituacao();
	}

	@Component
	public final class ValidacaoAlteracaoCedente
			extends JsonDeserializer<Pessoa> implements Serializable {

		private static final long serialVersionUID = 7520576152017963304L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoCedente(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Pessoa deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Pessoa cedente = jsonParser.getCodec()
					.readValue(jsonParser, Pessoa.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && cedente != null) {
					if (!inalterado.get().getCedente().equals(cedente)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"cedente\" inválido: Somente usuário administrador pode alterar cedente");
					}
				}
			}
			return cedente;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataEmissao
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 7904653060584108265L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoDataEmissao(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final LocalDate dataEmissao = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent()
						&& inalterado.get().getDataEmissao() != null) {
					if (!inalterado.get().getDataEmissao().equals(dataEmissao)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Atributo \"dataEmissao\" inválido: Somente usuário administrador pode alterar data de emissão");
					}
				}
			}
			return dataEmissao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataVencimento
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 5376928295995052436L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoDataVencimento(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final LocalDate dataVencimento = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && dataVencimento != null) {
					if (inalterado.get().getDataVencimento().until(dataVencimento, ChronoUnit.DAYS) != 0) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"dataVencimento\" inválido: Somente usuário administrador pode alterar data de vencimento");
					}
				}
			}
			return dataVencimento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoFatura extends JsonDeserializer<String>
			implements Serializable {

		private static final long serialVersionUID = 4325612702276449913L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoFatura(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public String deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final String fatura = jsonParser.getText();
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && fatura != null) {
					if (!inalterado.get().getFatura().equals(fatura)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"fatura\" inválido: Somente usuário administrador pode alterar fatura");
					}
				}
			}
			return fatura;
		}
	}

	@Component
	public final class ValidacaoAlteracaoMesReferente
			extends JsonDeserializer<YearMonth> implements Serializable {

		private static final long serialVersionUID = 364977890825931004L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoMesReferente(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public YearMonth deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final YearMonth mesReferente = jsonParser.getCodec()
					.readValue(jsonParser, YearMonth.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && mesReferente != null) {
					if (inalterado.get().getMesReferente().until(mesReferente, ChronoUnit.MONTHS) != 0) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"mesReferente\" inválido: Somente usuário administrador pode alterar mês referente");
					}
				}
			}
			return mesReferente;
		}
	}

	@Component
	public final class ValidacaoAlteracaoValor
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = -646927778181924816L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoValor(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final BigDecimal valor = jsonParser.getDecimalValue();
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaPagar> inalterado = ValidacaoAlteracaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && valor != null) {
					if (inalterado.get().getValor().compareTo(valor) != 0) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaPagar.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"valor\" inválido: Somente usuário administrador pode alterar valor");
					}
				}
			}
			return valor;
		}
	}
}
