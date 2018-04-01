package com.kinlhp.steve.api.servico.validacao;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.event.ValidatingRepositoryEventListener;
import org.springframework.validation.Validator;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;

@Configuration
public class ImplementacaoRegistradorEventosValidacao
		implements InitializingBean, Serializable {

	private static final long serialVersionUID = -7351362707194680353L;
	private final Map<String, Validator> validadores;
	private final ValidatingRepositoryEventListener validatingRepositoryEventListener;

	@Autowired
	public ImplementacaoRegistradorEventosValidacao(Map<String, Validator> validadores,
	                                                ValidatingRepositoryEventListener validatingRepositoryEventListener) {
		this.validadores = validadores;
		this.validatingRepositoryEventListener = validatingRepositoryEventListener;
	}

	@Override
	public void afterPropertiesSet() {
		Collection<String> eventos =
				new HashSet<String>(Arrays.asList("beforeCreate", "beforeSave"));
		for (Entry<String, Validator> validador : validadores.entrySet()) {
			eventos.stream()
					.filter(p -> validador.getKey().startsWith(p))
					.findFirst()
					.ifPresent(p -> validatingRepositoryEventListener.addValidator(p, validador.getValue()));
		}
	}
}
