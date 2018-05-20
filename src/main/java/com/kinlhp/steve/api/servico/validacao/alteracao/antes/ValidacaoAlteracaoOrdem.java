package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoOrdem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Locale;
import java.util.Optional;

@Component(value = "beforeSaveOrdem")
public class ValidacaoAlteracaoOrdem extends ValidacaoOrdem {

	private static final long serialVersionUID = 1061777002268988862L;

	public ValidacaoAlteracaoOrdem(@Autowired RepositorioOrdem repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return Ordem.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Ordem) object;
		super.erros = errors;

		validarCliente();
		validarSituacao();
	}

	@Component
	public final class ValidacaoAlteracaoSituacao
			extends JsonDeserializer<Ordem.Situacao> implements Serializable {

		private static final long serialVersionUID = 8096256824298069363L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoSituacao(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Ordem.Situacao deserialize(JsonParser jsonParser,
		                                  DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Ordem.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, Ordem.Situacao.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<Ordem> inalterado = ValidacaoAlteracaoOrdem.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && situacao != null) {
					if (!inalterado.get().getSituacao().equals(situacao)) {
						if (!CollectionUtils.isEmpty(inalterado.get().getContasReceber())
								|| Ordem.Situacao.GERADO.equals(inalterado.get().getSituacao())) {
							// TODO: 4/29/18 implementar internacionalização
							ValidacaoAlteracaoOrdem.super
									.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Ordem com conta a receber não pode ser alterado");
						} else if (Ordem.Situacao.CANCELADO.equals(inalterado.get().getSituacao())) {
							// TODO: 4/7/18 implementar internacionalização
							ValidacaoAlteracaoOrdem.this
									.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
											"Atributo \"situacao\" inválido: Somente usuário administrador pode alterar " + inalterado.get().getTipo().getDescricao().toLowerCase(Locale.ROOT) + " cancelado");
						} else if (Ordem.Situacao.FINALIZADO.equals(inalterado.get().getSituacao())
								&& Ordem.Situacao.ABERTO.equals(situacao)) {
							// TODO: 4/8/18 implementar internacionalização
							ValidacaoAlteracaoOrdem.this
									.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
											"Atributo \"situacao\" inválido: Somente usuário administrador pode reabrir " + inalterado.get().getTipo().getDescricao().toLowerCase(Locale.ROOT));
						}
					}
				}
			}
			return situacao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoTipo
			extends JsonDeserializer<Ordem.Tipo> implements Serializable {

		private static final long serialVersionUID = -2089864706053299735L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoTipo(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Ordem.Tipo deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Ordem.Tipo tipo = jsonParser.getCodec()
					.readValue(jsonParser, Ordem.Tipo.class);
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<Ordem> inalterado = ValidacaoAlteracaoOrdem.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent() && tipo != null) {
					if (Ordem.Tipo.ORDEM_SERVICO.equals(inalterado.get().getTipo())
							&& Ordem.Tipo.ORCAMENTO.equals(tipo)) {
						// TODO: 4/30/18 implementar internacionalização
						ValidacaoAlteracaoOrdem.super
								.erros.rejectValue("tipo", "tipo.invalid", "Atributo \"tipo\" inválido: Ordem não pode ser revertido em orçamento");
					}
				}
			}
			return tipo;
		}
	}
}
