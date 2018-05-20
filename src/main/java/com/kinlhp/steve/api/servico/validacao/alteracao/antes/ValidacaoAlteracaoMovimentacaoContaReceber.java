package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.MovimentacaoContaReceber;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaReceber;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaReceber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;

@Component(value = "beforeSaveMovimentacaoContaReceber")
public class ValidacaoAlteracaoMovimentacaoContaReceber
		extends ValidacaoMovimentacaoContaReceber {

	private static final long serialVersionUID = -1070554061628032646L;

	public ValidacaoAlteracaoMovimentacaoContaReceber(@Autowired RepositorioMovimentacaoContaReceber repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaReceber.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaReceber) object;
		super.erros = errors;

		validarEstornado();
	}

	private void validarEstornado() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"estornado\" inválido: Somente usuário administrador pode estornar movimentação de conta a receber");
		}
	}

	@Component
	public final class ValidacaoAlteracaoEstornado
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = -9126601244240384854L;
		private final HttpServletRequest requisicao;

		public ValidacaoAlteracaoEstornado(@Autowired HttpServletRequest requisicao) {
			this.requisicao = requisicao;
		}

		@Override
		public Boolean deserialize(JsonParser jsonParser,
		                           DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			final boolean estornado = jsonParser.getBooleanValue();
			final String id = new File(requisicao.getRequestURI()).getName();
			if (id.chars().noneMatch(Character::isLetter)) {
				final Optional<MovimentacaoContaReceber> inalterado = ValidacaoAlteracaoMovimentacaoContaReceber.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent()) {
					if (inalterado.get().isEstornado() && !estornado) {
						// TODO: 5/15/18 implementar internacionalização
						ValidacaoAlteracaoMovimentacaoContaReceber.super
								.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Estorno de movimentação de conta a receber não pode ser cancelado");
					}
				}
			}
			return estornado;
		}
	}
}
