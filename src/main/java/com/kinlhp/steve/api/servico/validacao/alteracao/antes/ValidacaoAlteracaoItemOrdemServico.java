package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Servico;
import com.kinlhp.steve.api.repositorio.RepositorioItemOrdemServico;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
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
import java.util.Optional;

@Component(value = "beforeSaveItemOrdemServico")
public class ValidacaoAlteracaoItemOrdemServico
		extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = 4002419876156930844L;

	public ValidacaoAlteracaoItemOrdemServico(@Autowired RepositorioItemOrdemServico repositorio,
	                                          @Autowired RepositorioOrdem repositorioOrdem) {
		super(repositorio, repositorioOrdem);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return ItemOrdemServico.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ItemOrdemServico) object;
		super.erros = errors;

		validarOrdem();
	}

	@Component
	public final class ValidacaoAlteracaoDataFinalizacaoPrevista
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = 2768276775758297104L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoDataFinalizacaoPrevista(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final LocalDate dataFinalizacaoPrevista = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ItemOrdemServico> inalterado = ValidacaoAlteracaoItemOrdemServico.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent()
						&& inalterado.get().getDataFinalizacaoPrevista() != null) {
					if (!inalterado.get().getDataFinalizacaoPrevista().equals(dataFinalizacaoPrevista)) {
						// TODO: 5/1/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Atributo \"dataFinalizacaoPrevista\" inválido: Somente usuário administrador pode alterar data de finalização prevista");
					}
				}
			}
			return dataFinalizacaoPrevista;
		}
	}

	@Component
	public final class ValidacaoAlteracaoServico
			extends JsonDeserializer<Servico> implements Serializable {

		private static final long serialVersionUID = -7066604115452210160L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoServico(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Servico deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Servico servico = jsonParser.getCodec()
					.readValue(jsonParser, Servico.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ItemOrdemServico> inalterado = ValidacaoAlteracaoItemOrdemServico.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && servico != null) {
					if (ItemOrdemServico.Situacao.CANCELADO.equals(inalterado.get().getSituacao())
							&& !inalterado.get().getServico().equals(servico)) {
						// TODO: 5/15/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"servico\" inválido: Somente usuário administrador pode alterar serviço de item cancelado");
					} else if (ItemOrdemServico.Situacao.FINALIZADO.equals(inalterado.get().getSituacao())
							&& !inalterado.get().getServico().equals(servico)) {
						// TODO: 5/15/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"servico\" inválido: Somente usuário administrador pode alterar serviço de item finalizado");
					}
				}
			}
			return servico;
		}
	}

	@Component
	public final class ValidacaoAlteracaoSituacao
			extends JsonDeserializer<ItemOrdemServico.Situacao>
			implements Serializable {

		private static final long serialVersionUID = -1286300349903296298L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoSituacao(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public ItemOrdemServico.Situacao deserialize(JsonParser jsonParser,
		                                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, ItemOrdemServico.Situacao.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ItemOrdemServico> inalterado = ValidacaoAlteracaoItemOrdemServico.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && situacao != null) {
					if (ItemOrdemServico.Situacao.CANCELADO.equals(inalterado.get().getSituacao())) {
						// TODO: 4/30/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"situação\" inválido: Somente usuário administrador pode alterar situação de item cancelado");
					} else if (!ItemOrdemServico.Situacao.FINALIZADO.equals(inalterado.get().getSituacao())) {
						// TODO: 4/30/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"situação\" inválido: Somente usuário administrador pode alterar situação de item finalizado");
					}
				}
			}
			return situacao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoValorServico
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = 4092792770203919061L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoValorServico(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final BigDecimal valorServico = jsonParser.getDecimalValue();
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<ItemOrdemServico> inalterado = ValidacaoAlteracaoItemOrdemServico.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && valorServico != null) {
					if (ItemOrdemServico.Situacao.CANCELADO.equals(inalterado.get().getSituacao())
							&& inalterado.get().getValorServico().compareTo(valorServico) != 0) {
						// TODO: 4/30/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"valorServico\" inválido: Somente usuário administrador pode alterar valor do serviço de item cancelado");
					} else if (ItemOrdemServico.Situacao.FINALIZADO.equals(inalterado.get().getSituacao())
							&& inalterado.get().getValorServico().compareTo(valorServico) != 0) {
						// TODO: 4/30/18 implementar internacionalização
						ValidacaoAlteracaoItemOrdemServico.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"valorServico\" inválido: Somente usuário administrador pode alterar valor do serviço de item finalizado");
					}
				}
			}
			return valorServico;
		}
	}
}
