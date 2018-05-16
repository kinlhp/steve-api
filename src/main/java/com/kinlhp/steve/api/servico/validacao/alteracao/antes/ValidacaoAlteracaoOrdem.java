package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoOrdem;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

@Component(value = "beforeSaveOrdem")
public class ValidacaoAlteracaoOrdem extends ValidacaoOrdem {

	private static final long serialVersionUID = -192236684187545639L;

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

		private static final long serialVersionUID = -8227229861549022454L;

		@Override
		public Ordem.Situacao deserialize(JsonParser jsonParser,
		                                  DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Ordem registroInalterado = (Ordem) jsonParser
					.getCurrentValue();
			final Ordem.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, Ordem.Situacao.class);
			if (registroInalterado.getId() != null && situacao != null) {
				if (!registroInalterado.getSituacao().equals(situacao)) {
					if (!CollectionUtils.isEmpty(registroInalterado.getContasReceber())
							|| Ordem.Situacao.GERADO.equals(registroInalterado.getSituacao())) {
						// TODO: 4/29/18 implementar internacionalização
						ValidacaoAlteracaoOrdem.this
								.erros.rejectValue("situacao", "situacao.invalid", "Atributo \"situacao\" inválido: Ordem com conta a receber não pode ser alterado");
					} else if (Ordem.Situacao.CANCELADO.equals(registroInalterado.getSituacao())) {
						// TODO: 4/7/18 implementar internacionalização
						ValidacaoAlteracaoOrdem.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"situacao\" inválido: Somente usuário administrador pode alterar " + registroInalterado.getTipo().getDescricao().toLowerCase(Locale.ROOT) + " cancelado");
					} else if (Ordem.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())
							&& Ordem.Situacao.ABERTO.equals(situacao)) {
						// TODO: 4/8/18 implementar internacionalização
						ValidacaoAlteracaoOrdem.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"situacao\" inválido: Somente usuário administrador pode reabrir " + registroInalterado.getTipo().getDescricao().toLowerCase(Locale.ROOT));
					}
				}
			}
			return situacao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoTipo
			extends JsonDeserializer<Ordem.Tipo> implements Serializable {

		private static final long serialVersionUID = 117953327388787611L;

		@Override
		public Ordem.Tipo deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Ordem registroInalterado = (Ordem) jsonParser
					.getCurrentValue();
			final Ordem.Tipo tipo = jsonParser.getCodec()
					.readValue(jsonParser, Ordem.Tipo.class);
			if (registroInalterado.getId() != null && tipo != null) {
				if (Ordem.Tipo.ORDEM_SERVICO.equals(registroInalterado.getTipo())
						&& Ordem.Tipo.ORCAMENTO.equals(tipo)) {
					// TODO: 4/30/18 implementar internacionalização
					ValidacaoAlteracaoOrdem.this
							.erros.rejectValue("tipo", "tipo.invalid", "Atributo \"tipo\" inválido: Ordem não pode ser revertido em orçamento");
				}
			}
			return tipo;
		}
	}
}
