package com.kinlhp.steve.api.servico.validacao.alteracao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.servico.validacao.ValidacaoOrdem;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;
import java.util.Locale;

@Component(value = "beforeSaveOrdem")
public class ValidacaoAlteracaoOrdem extends ValidacaoOrdem {

	private static final long serialVersionUID = -7752331168641538075L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Ordem.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Ordem) object;
		super.erros = errors;

		// TODO: 4/7/18 implementar design pattern que resolva essa má prática
		validarCliente();
		validarSituacao();
	}

	// TODO: 4/7/18 corrigir essa gambiarra e continuar não permitindo que um usuário não administrador altere a situação de uma ordem de serviço para uma situação inconsistente
	@Component
	public final class ValidacaoAlteracaoSituacao
			extends JsonDeserializer<Ordem.Situacao> implements Serializable {

		private static final long serialVersionUID = -4752904628546368100L;

		@Override
		public Ordem.Situacao deserialize(JsonParser jsonParser,
		                                  DeserializationContext deserializationContext)
				throws IOException {
			final Ordem registroInalterado = (Ordem) jsonParser
					.getCurrentValue();
			final Ordem.Situacao situacao = jsonParser.getCodec()
					.readValue(jsonParser, Ordem.Situacao.class);
			if (registroInalterado.getId() != null && situacao != null) {
				if (!Ordem.Situacao.ABERTO.equals(registroInalterado.getSituacao())
						&& !Ordem.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())) {
					// TODO: 4/7/18 implementar internacionalizacao
					verificarPermissao(Permissao.Descricao.ADMINISTRADOR, "Somente usuário administrador pode alterar ordem em situação cancelado ou gerado");
				} else if (Ordem.Situacao.FINALIZADO.equals(registroInalterado.getSituacao())) {
					if (!registroInalterado.getSituacao().equals(situacao)
							&& Ordem.Situacao.ABERTO.equals(situacao)) {
						// TODO: 4/8/18 implementar internacionalizacao
						verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
								"Somente usuário administrador pode reabrir " + ValidacaoAlteracaoOrdem.super.dominio.getTipo().getDescricao().toLowerCase(Locale.ROOT));
					}
				}
			}
			return situacao;
		}
	}
}
