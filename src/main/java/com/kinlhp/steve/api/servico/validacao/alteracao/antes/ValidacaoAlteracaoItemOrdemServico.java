package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Servico;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import com.kinlhp.steve.api.servico.validacao.ValidacaoItemOrdemServico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Component(value = "beforeSaveItemOrdemServico")
public class ValidacaoAlteracaoItemOrdemServico
		extends ValidacaoItemOrdemServico {

	private static final long serialVersionUID = -3021103320699988816L;

	public ValidacaoAlteracaoItemOrdemServico(@Autowired RepositorioOrdem repositorioOrdem) {
		super(repositorioOrdem);
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

		private static final long serialVersionUID = -5158824466814254446L;

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
					// TODO: 5/1/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Atributo \"dataFinalizacaoPrevista\" inválido: Somente usuário administrador pode alterar data de finalização prevista");
				}
			}
			return dataFinalizacaoPrevista;
		}
	}

	@Component
	public final class ValidacaoAlteracaoServico
			extends JsonDeserializer<Servico> implements Serializable {

		private static final long serialVersionUID = -3598273852185025476L;

		@Override
		public Servico deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final Servico servico = jsonParser.getCodec()
					.readValue(jsonParser, Servico.class);
			if (registroInalterado.getId() != null && servico != null) {
				if (ItemOrdemServico.Situacao.CANCELADO.equals(registroInalterado.getSituacao())
						&& !registroInalterado.getServico().equals(servico)) {
					// TODO: 5/15/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"servico\" inválido: Somente usuário administrador pode alterar serviço de item cancelado");
				} else if (ItemOrdemServico.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())
						&& !registroInalterado.getServico().equals(servico)) {
					// TODO: 5/15/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"servico\" inválido: Somente usuário administrador pode alterar serviço de item finalizado");
				}
			}
			return servico;
		}
	}

	@Component
	public final class ValidacaoAlteracaoSituacao
			extends JsonDeserializer<ItemOrdemServico.Situacao>
			implements Serializable {

		private static final long serialVersionUID = 3270394410698278233L;

		@Override
		public ItemOrdemServico.Situacao deserialize(JsonParser jsonParser,
		                                             DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final ItemOrdemServico.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, ItemOrdemServico.Situacao.class);
			if (registroInalterado.getId() != null && situacao != null) {
				if (ItemOrdemServico.Situacao.CANCELADO.equals(registroInalterado.getSituacao())) {
					// TODO: 4/30/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"situação\" inválido: Somente usuário administrador pode alterar situação de item cancelado");
				} else if (!ItemOrdemServico.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())) {
					// TODO: 4/30/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"situação\" inválido: Somente usuário administrador pode alterar situação de item finalizado");
				}
			}
			return situacao;
		}
	}

	@Component
	public final class ValidacaoAlteracaoValorServico
			extends JsonDeserializer<BigDecimal> implements Serializable {

		private static final long serialVersionUID = 1818519439675405587L;

		@Override
		public BigDecimal deserialize(JsonParser jsonParser,
		                              DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final ItemOrdemServico registroInalterado = (ItemOrdemServico) jsonParser
					.getCurrentValue();
			final BigDecimal valorServico = jsonParser.getDecimalValue();
			if (registroInalterado.getId() != null && valorServico != null) {
				if (ItemOrdemServico.Situacao.CANCELADO.equals(registroInalterado.getSituacao())
						&& registroInalterado.getValorServico().compareTo(valorServico) != 0) {
					// TODO: 4/30/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"valorServico\" inválido: Somente usuário administrador pode alterar valor do serviço de item cancelado");
				} else if (ItemOrdemServico.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())
						&& registroInalterado.getValorServico().compareTo(valorServico) != 0) {
					// TODO: 4/30/18 implementar internacionalização
					ValidacaoAlteracaoItemOrdemServico.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"valorServico\" inválido: Somente usuário administrador pode alterar valor do serviço de item finalizado");
				}
			}
			return valorServico;
		}
	}
}
