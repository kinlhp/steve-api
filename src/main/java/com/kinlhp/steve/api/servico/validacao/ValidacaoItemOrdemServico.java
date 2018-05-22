package com.kinlhp.steve.api.servico.validacao;

import com.kinlhp.steve.api.dominio.ItemOrdemServico;
import com.kinlhp.steve.api.dominio.Ordem;
import com.kinlhp.steve.api.dominio.Permissao;
import com.kinlhp.steve.api.repositorio.RepositorioItemOrdemServico;
import com.kinlhp.steve.api.repositorio.RepositorioOrdem;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.Locale;
import java.util.Set;

public abstract class ValidacaoItemOrdemServico
		extends ValidavelAbstrato<ItemOrdemServico, BigInteger> {

	private static final long serialVersionUID = 8864392738323873462L;
	private final RepositorioOrdem repositorioOrdem;

	public ValidacaoItemOrdemServico(@Autowired RepositorioItemOrdemServico repositorio,
	                                 @Autowired RepositorioOrdem repositorioOrdem) {
		super(repositorio);
		this.repositorioOrdem = repositorioOrdem;
	}

	protected void finalizarOuReabrirOrdem() {
		final Ordem ordem = super.dominio.getOrdem();
		final Set<ItemOrdemServico> itens = ((RepositorioItemOrdemServico) super.repositorio)
				.findByOrdem(ordem);
		final long quantidadeItemAberto = itens.stream()
				.filter(p -> ItemOrdemServico.Situacao.ABERTO.equals(p.getSituacao()))
				.count();
		if (Ordem.Situacao.ABERTO.equals(ordem.getSituacao())
				&& quantidadeItemAberto == 0) {
			final long quantidadeItemFinalizado = itens.stream()
					.filter(p -> ItemOrdemServico.Situacao.FINALIZADO.equals(p.getSituacao()))
					.count();
			ordem.setSituacao(quantidadeItemFinalizado == 0 ? Ordem.Situacao.CANCELADO : Ordem.Situacao.FINALIZADO);
		} else if (!Ordem.Situacao.GERADO.equals(ordem.getSituacao())
				&& quantidadeItemAberto != 0) {
			ordem.setSituacao(Ordem.Situacao.ABERTO);
		} else {
			return;
		}
		repositorioOrdem.saveAndFlush(ordem);
	}

	protected void validarOrdem() {
		if (super.dominio.getOrdem() != null) {
			if (!Ordem.Situacao.ABERTO.equals(super.dominio.getOrdem().getSituacao())) {
				// TODO: 5/15/18 implementar internacionalização
				super.erros.rejectValue("ordem", "ordem.invalid", "Atributo \"ordem\" inválido: Ordem " + super.dominio.getOrdem().getSituacao().getDescricao().toLowerCase(Locale.ROOT) + " não deve ter " + (super.dominio.getOrdem().getId() != null ? "item alterado" : "novo item"));
			}
		}
	}

	protected void validarSituacao() {
		if (super.dominio.getSituacao() != null) {
			if (!ItemOrdemServico.Situacao.ABERTO.equals(super.dominio.getSituacao())) {
				if (ItemOrdemServico.Situacao.CANCELADO.equals(super.dominio.getSituacao())) {
					// TODO: 4/7/18 implementar internacionalização
					super.verificarPermissao(Permissao.Descricao.ADMINISTRADOR,
							"Atributo \"situacao\" inválido: Somente usuário administrador pode cancelar item");
				}
			}
		}
	}
}
