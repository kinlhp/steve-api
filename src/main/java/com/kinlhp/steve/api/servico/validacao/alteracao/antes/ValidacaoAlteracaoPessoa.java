package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.dominio.Pessoa;
import com.kinlhp.steve.api.repositorio.RepositorioPessoa;
import com.kinlhp.steve.api.servico.validacao.ValidacaoPessoa;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;

@Component(value = "beforeSavePessoa")
public class ValidacaoAlteracaoPessoa extends ValidacaoPessoa {

	private static final long serialVersionUID = -4866327123681612743L;

	public ValidacaoAlteracaoPessoa(@Autowired RepositorioPessoa repositorio) {
		super(repositorio);
	}

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

		private static final long serialVersionUID = -8792776545930221565L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoPerfilUsuario(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Boolean deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final boolean perfilUsuario = jsonParser.getBooleanValue();
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<Pessoa> inalterado = ValidacaoAlteracaoPessoa.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent()) {
					if (inalterado.get().isPerfilUsuario() != perfilUsuario) {
						// TODO: 4/5/18 implementar internacionalização
						ValidacaoAlteracaoPessoa.this
								.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
										"Atributo \"perfilUsuario\" inválido: Somente usuário administrador pode alterar pessoa com perfil de usuário");
					}
				}
			}
			return perfilUsuario;
		}
	}
}
