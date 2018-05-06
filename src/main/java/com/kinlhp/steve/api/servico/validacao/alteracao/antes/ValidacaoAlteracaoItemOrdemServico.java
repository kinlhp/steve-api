package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component(value = "beforeSaveItemOrdemServico")
public class ValidacaoAlteracaoItemOrdemServico
		extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = -4313253099209991251L;

	@Override
	public boolean supports(Class<?> clazz) {
		return ItemOrdemServico.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (ItemOrdemServico) object;
		super.erros = errors;
	}

	@Component
	public final class ValidacaoAlteracaoDataFinalizacaoPrevista
			extends JsonDeserializer<LocalDate> implements Serializable {

		private static final long serialVersionUID = -8157638132687101075L;

		@Override
		public LocalDate deserialize(JsonParser jsonParser,
		                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final LocalDate dataFinalizacaoPrevista = jsonParser.getCodec()
					.readValue(jsonParser, LocalDate.class);
			if (registroInalterado.getId() != null
					&& registroInalterado.getDataFinalizacaoPrevista() != null) {
				if (!registroInalterado.getDataFinalizacaoPrevista().equals(dataFinalizacaoPrevista)) {
					// TODO: 5/1/18 implementar internacionalizacao
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Atributo \"dataFinalizacaoPrevista\" inválido: Somente usuário administrador pode alterar data de finalização prevista");
				}
			}
			return dataFinalizacaoPrevista;
		}
	}

	// TODO: 4/30/18 corrigir essa gambiarra e continuar não permitindo que um usuário não administrador altere a situação do item da ordem de serviço para uma situação inconsistente
	@Component
	public final class ValidacaoAlteracaoSituacao
			extends JsonDeserializer<ItemOrdemServico.Situacao>
			implements Serializable {

		private static final long serialVersionUID = 5441584290632281474L;

		@Override
		public ItemOrdemServico.Situacao deserialize(JsonParser jsonParser,
		                                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final ItemOrdemServico.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, ItemOrdemServico.Situacao.class);
			if (registroInalterado.getId() != null && situacao != null) {
				if (!ItemOrdemServico.Situacao.ABERTO.equals(registroInalterado.getSituacao())) {
					// TODO: 4/30/18 implementar internacionalizacao
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"situação\" inválido: Somente usuário administrador pode alterar situação de item cancelado ou finalizado");
				}
			}
			return situacao;
		}
	}

	// TODO: 4/30/18 corrigir essa gambiarra e continuar não permitindo que um usuário não administrador altere o valor de item cancelado ou finalizado
	@Component
	public final class ValidacaoAlteracaoValorServico
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = 3174192658967181091L;

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final BigDecimal valorServico = jsonParser.getDecimalValue();
			if (registroInalterado.getId() != null && valorServico != null) {
				if (!ItemOrdemServico.Situacao.ABERTO.equals(registroInalterado.getSituacao())
						&& registroInalterado.getValorServico().compareTo(valorServico) != 0) {
					// TODO: 4/30/18 implementar internacionalizacao
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"valorServico\" inválido: Somente usuário administrador pode alterar valor do serviço de item cancelado ou finalizado");
				}
			}
			return valorServico;
		}
	}
}