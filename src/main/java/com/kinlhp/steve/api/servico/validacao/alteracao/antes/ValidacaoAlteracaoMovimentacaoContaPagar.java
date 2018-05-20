package com.kinlhp.steve.api.servico.validacao.alteracao.antes;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.kinlhp.steve.api.dominio.MovimentacaoContaPagar;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioMovimentacaoContaPagar;
import com.kinlhp.steve.api.servico.validacao.ValidacaoMovimentacaoContaPagar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Optional;

@Component(value = "beforeSaveMovimentacaoContaPagar")
public class ValidacaoAlteracaoMovimentacaoContaPagar
		extends ValidacaoMovimentacaoContaPagar {

	private static final long serialVersionUID = 7732579936471910333L;

	public ValidacaoAlteracaoMovimentacaoContaPagar(@Autowired RepositorioMovimentacaoContaPagar repositorio) {
		super(repositorio);
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return MovimentacaoContaPagar.class.equals(clazz);
	}

	@Override
	public void validate(Object object, Errors errors) {
		super.dominio = (MovimentacaoContaPagar) object;
		super.erros = errors;

		validarEstornado();
	}

	private void validarEstornado() {
		if (super.dominio.isEstornado()) {
			// TODO: 5/5/18 implementar internacionalização
			super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
					"Atributo \"estornado\" inválido: Somente usuário administrador pode estornar movimentação de conta a pagar");
		}
	}

	@Component
	public final class ValidacaoAlteracaoEstornado
			extends JsonDeserializer<Boolean> implements Serializable {

		private static final long serialVersionUID = 6006075115539097146L;
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
				final Optional<MovimentacaoContaPagar> inalterado = ValidacaoAlteracaoMovimentacaoContaPagar.super
						.verificarExistencia(new BigInteger(id));
				if (inalterado.isPresent()) {
					if (inalterado.get().isEstornado() && !estornado) {
						// TODO: 5/15/18 implementar internacionalização
						ValidacaoAlteracaoMovimentacaoContaPagar.super
								.erros.rejectValue("estornado", "estornado.invalid", "Atributo \"estornado\" inválido: Estorno de movimentação de conta a pagar não pode ser cancelado");
					}
				}
			}
			return estornado;
		}
	}
}
