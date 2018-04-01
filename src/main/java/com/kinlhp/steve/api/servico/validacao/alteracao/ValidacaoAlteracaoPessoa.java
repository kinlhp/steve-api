package com.kinlhp.steve.api.servico.validacao.alteracao;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPessoa;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;

@Component(value = "beforeSavePessoa")
public class ValidacaoAlteracaoPessoa extends ValidacaoPessoa {

	private static final long serialVersionUID = 1457817883262828945L;

	@Override
	public boolean supports(Class<?> classe) {
		return Pessoa.class.equals(classe);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Pessoa) object;
		super.erros = errors;

		// TODO: 3/28/18 implementar design pattern que resolva essa má prática
		validarAberturaNascimento();
		validarCnpjCpf();
		validarIeRg();
		validarTipo();
	}

	// TODO: 3/31/18 corrigir essa gambiarra e continuar não permitindo que um usuário não administrador altere o perfil de usuário de uma pessoa
	@Component
	public final class ValidacaoAlteracaoPerfilUsuario
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = 6653142342814346849L;

		@Override
		public Boolean deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
			final Pessoa registroInalterado = (Pessoa) jsonParser.getCurrentValue();
			final boolean perfilUsuario = jsonParser.getBooleanValue();
			if (registroInalterado.getId() != null) {
				ValidacaoAlteracaoPessoa.this.verificarPermissao(Permissao.Descricao.ADMINISTRADOR);
				if (registroInalterado.isPerfilUsuario() != perfilUsuario) {
					throw new AccessDeniedException(mensagem.getMessage(
							"JdbcDaoImpl.noAuthority",
							new Object[]{ValidacaoAlteracaoPessoa.this.autenticacao.getPrincipal()},
							"Access is denied"));
				}
			}
			return perfilUsuario;
		}
	}
}
