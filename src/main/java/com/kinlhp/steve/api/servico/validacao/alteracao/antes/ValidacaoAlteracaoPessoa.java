package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPessoa;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import java.io.IOException;
import java.io.Serializable;

@Component(value = "beforeSavePessoa")
public class ValidacaoAlteracaoPessoa extends ValidacaoPessoa {

	private static final long serialVersionUID = 9174522398382070830L;

	@Override
	public boolean supports(Class<?> clazz) {
		return Pessoa.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (Pessoa) object;
		super.erros = errors;

		validarAberturaNascimento();
		validarCnpjCpf();
		validarIeRg();
		validarTipo();
	}

	@Component
	public final class ValidacaoAlteracaoPerfilUsuario
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = -3123589793222672728L;

		@Override
		public Boolean deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final Pessoa registroInalterado = (Pessoa) jsonParser
					.getCurrentValue();
			final boolean perfilUsuario = jsonParser.getBooleanValue();
			if (registroInalterado.getId() != null) {
				if (registroInalterado.isPerfilUsuario() != perfilUsuario) {
					// TODO: 4/5/18 implementar internacionalização
					ValidacaoAlteracaoPessoa.this
							.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
									"Atributo \"perfilUsuario\" inválido: Somente usuário administrador pode alterar pessoa com perfil de usuário");
				}
			}
			return perfilUsuario;
		}
	}
}
