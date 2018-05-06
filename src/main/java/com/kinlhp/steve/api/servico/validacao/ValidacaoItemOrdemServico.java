package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import java.util.Locale;

public abstract class ValidacaoItemOrdemServico
		extends ValidavelAbstrato<ItemOrdemServico> {

	private static final long serialVersionUID = 6464850295325183718L;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private RepositorioOrdem repositorioOrdem;

	protected void finalizarOuReabrirOrdem() {
		final Ordem ordem = super.dominio.getOrdem();
		final long quantidadeItemAberto = ordem.getItens()
				.stream()
				.filter(p -> ItemOrdemServico.Situacao.ABERTO.equals(p.getSituacao()))
				.count();
		if (Ordem.Situacao.ABERTO.equals(ordem.getSituacao())
				&& quantidadeItemAberto == 0) {
			final long quantidadeItemFinalizado = ordem.getItens()
					.stream()
					.filter(p -> ItemOrdemServico.Situacao.FINALIZADO.equals(p.getSituacao()))
					.count();
			ordem.setSituacao(quantidadeItemFinalizado == 0 ? Ordem.Situacao.CANCELADO : Ordem.Situacao.FINALIZADO);
		} else if (!Ordem.Situacao.GERADO.equals(ordem.getSituacao())
				&& quantidadeItemAberto != 0) {
			ordem.setSituacao(Ordem.Situacao.ABERTO);
		} else {
			return;
		}
		try {
			entityManager.setFlushMode(FlushModeType.COMMIT);
			repositorioOrdem.saveAndFlush(ordem);
		} finally {
			entityManager.setFlushMode(FlushModeType.AUTO);
		}
	}

	protected void validarOrdem() {
		if (super.dominio.getOrdem() != null) {
			if (!Ordem.Situacao.ABERTO.equals(super.dominio.getOrdem().getSituacao())) {
				super.erros.rejectValue("ordem", "ordem.invalid", "Atributo \"ordem\" inválido: Ordem " + super.dominio.getOrdem().getSituacao().getDescricao().toLowerCase(Locale.ROOT) + " não deve ter " + (super.dominio.getOrdem().getId() != null ? "item alterado" : "novo item"));
			}
		}
	}
}
