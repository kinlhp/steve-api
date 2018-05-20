package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.CondicaoPagamento;
import com.kinlhp.steve.api.dominio.ContaReceber;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.repositorio.RepositorioContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoContaReceber;
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
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Component(value = "beforeSaveContaReceber")
public class ValidacaoAlteracaoContaReceber extends ValidacaoContaReceber {

	private static final long serialVersionUID = 1578062868126762477L;

	public ValidacaoAlteracaoContaReceber(@Autowired RepositorioContaReceber repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ContaReceber) object;
		super.erros = errors;

		validarOrdem();
		validarSituacao();
	}

	@Component
	public final class ValidacaoAlteracaoCondicaoPagamento
			extends JsonDeserializer<CondicaoPagamento> implements Serializable {

		private static final long serialVersionUID = 459409007316191693L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoCondicaoPagamento(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public CondicaoPagamento deserialize(JsonParser jsonParser,
		                                     DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final CondicaoPagamento condicaoPagamento = jsonParser.getCodec()
					.readValue(jsonParser, CondicaoPagamento.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaReceber> inalterado = ValidacaoAlteracaoContaReceber.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && condicaoPagamento != null) {
					if (!inalterado.get().getCondicaoPagamento().equals(condicaoPagamento)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaReceber.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"condicaoPagamento\" inválido: Somente usuário administrador pode alterar condição de pagamento");
					}
				}
			}
			return condicaoPagamento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoDataVencimento
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 176121753561742753L;
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
				final Optional<ContaReceber> inalterado = ValidacaoAlteracaoContaReceber.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && dataVencimento != null) {
					if (inalterado.get().getDataVencimento().until(dataVencimento, ChronoUnit.DAYS) != 0) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaReceber.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"dataVencimento\" inválido: Somente usuário administrador pode alterar data de vencimento");
					}
				}
			}
			return dataVencimento;
		}
	}

	@Component
	public final class ValidacaoAlteracaoSacado
			extends JsonDeserializer<Pessoa> implements Serializable {

		private static final long serialVersionUID = 6215195607958210605L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoSacado(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Pessoa deserialize(JsonParser jsonParser,
		                          DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Pessoa sacado = jsonParser.getCodec()
					.readValue(jsonParser, Pessoa.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ContaReceber> inalterado = ValidacaoAlteracaoContaReceber.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && sacado != null) {
					if (!inalterado.get().getSacado().equals(sacado)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaReceber.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"sacado\" inválido: Somente usuário administrador pode alterar sacado");
					}
				}
			}
			return sacado;
		}
	}

	@Component
	public final class ValidacaoAlteracaoValor
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = -9070762896697853673L;
		final HttpServletRequest requisicao;

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
				final Optional<ContaReceber> inalterado = ValidacaoAlteracaoContaReceber.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && valor != null) {
					if (inalterado.get().getValor().compareTo(valor) != 0) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoContaReceber.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"valor\" inválido: Somente usuário administrador pode alterar valor");
					}
				}
			}
			return valor;
		}
	}
}
